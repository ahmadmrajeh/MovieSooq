package com.example.datascources.retrofit

 import com.example.datascources.util.API_KEY
import com.example.modelsmodule.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequests {

    @GET("trending/movie/day")
    suspend fun getMoviesListApi(

        @Query("api_key") apiKey: String = API_KEY,

        ): Response<MovieResponse>

}
