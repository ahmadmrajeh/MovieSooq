package com.example.moviesooq.ui.homeScreen

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.datascources.realm_db.MoviesRealm
import com.example.datascources.realm_db.ResultRealm
import com.example.datascources.repository.Repository
import com.example.datascources.util.NetworkListener
import com.example.datascources.util.NetworkResult
import com.example.modelsmodule.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val repository: Repository,
    application: Application
)    : AndroidViewModel(application) {



    /**  Retrofit  */
    var movieResponse: MutableLiveData<NetworkResult<MovieResponse>> = MutableLiveData()
    var result: MutableLiveData<RealmList<ResultRealm>> = MutableLiveData()


    fun readMoviesList() = viewModelScope.launch {
        getMoviesListSafeCall( )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun getMoviesListSafeCall() {
        movieResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.RetrieveMovies()

                movieResponse.value = NetworkListener. handleResponse(response)

                val currentMoviesList =   movieResponse.value!!.data
                if (currentMoviesList != null) {
                    offlineCache(currentMoviesList)
                }

            } catch (e: Exception) {

                movieResponse.value =NetworkResult.Error("List of Movies not found.")
            }
        } else {

            movieResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }


    private fun offlineCache(movieResponse: MovieResponse) {

        viewModelScope.launch (Dispatchers.IO) {
            val db = Realm.getDefaultInstance()
            repository.insertToRealm(movieResponse,db)

        }

    }


     fun readOfflineCache() {

         viewModelScope.launch(Dispatchers.Main) {

            val db: Realm=Realm.getDefaultInstance()
            val data=db.where(MoviesRealm::class.java)?.findFirst()?.results
            data?.let { result.postValue(it)
            }
        }

    }


    private fun hasInternetConnection(): Boolean {

        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

            else -> false
        }
    }


}