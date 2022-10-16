package com.example.moviesooq.ui.homeScreen

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.datascources.repository.Repository
import com.example.datascources.util.NetworkResult
import com.example.modelsmodule.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val repository: Repository,
    application: Application
)    : AndroidViewModel(application) {



    /**  Retrofit  */
    var movieResponse: MutableLiveData<NetworkResult<MovieResponse>> = MutableLiveData()


    fun readMoviesList() = viewModelScope.launch {
        getMoviesListSafeCall( )
    }

    private suspend fun getMoviesListSafeCall() {
        movieResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.RetrieveMovies()

                movieResponse.value = handleResponse(response)

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


    private fun handleResponse(response: Response<MovieResponse>): NetworkResult<MovieResponse> {

        when {
            response.message().toString().contains("timeout") -> {
                return  NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return  NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.results.isEmpty() -> {
                return  NetworkResult.Error("List of Movies not found.")
            }
            response.isSuccessful -> {

                return  NetworkResult.Success(response.body()!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
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