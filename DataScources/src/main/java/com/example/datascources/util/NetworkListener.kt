package com.example.datascources.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.example.modelsmodule.MovieResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response

@ExperimentalCoroutinesApi
class NetworkListener : ConnectivityManager.NetworkCallback() {

    private val isNetworkAvailable = MutableStateFlow(false)

    fun checkNetworkAvailability(context: Context): MutableStateFlow<Boolean> {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        val network =
            connectivityManager.activeNetwork
        if (network == null) {
            isNetworkAvailable.value = false
            return isNetworkAvailable
        }

        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if (networkCapabilities == null) {
            isNetworkAvailable.value = false
            return isNetworkAvailable
        }

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                isNetworkAvailable.value = true
                isNetworkAvailable
            }
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                isNetworkAvailable.value = true
                isNetworkAvailable
            }
            else -> {
                isNetworkAvailable.value = false
                isNetworkAvailable
            }
        }
    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.value = false
    }


    companion object {
        @JvmStatic
        fun handleResponse(response: Response<MovieResponse>): NetworkResult<MovieResponse> {

            when {
                response.message().toString().contains("timeout") -> {
                    return NetworkResult.Error("Timeout")
                }

                response.code() == 402 -> {
                    return NetworkResult.Error("API Key Limited.")
                }
                response.body()!!.results.isEmpty() -> {
                    return NetworkResult.Error("List of Movies not found.")
                }
                response.isSuccessful -> {

                    return NetworkResult.Success(response.body()!!)
                }
                else -> {
                    return NetworkResult.Error(response.message())
                }
            }
        }

    }

}
