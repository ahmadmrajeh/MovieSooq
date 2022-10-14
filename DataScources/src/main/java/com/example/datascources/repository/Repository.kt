package com.example.datascources.repository

import com.example.datascources.realm_db.RealmOperations
import com.example.datascources.retrofit.ApiRequests
import com.example.modelsmodule.MovieResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.realm.Realm
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val apiRequests: ApiRequests,
) {
    // insert into realm
     suspend fun insertToRealm(movieToDatabase: MovieResponse, db: Realm) {
        val operationsObject = RealmOperations()
        operationsObject.insertListIntoRealm(movieToDatabase, db)
    }


    //retrofit
    suspend fun RetrieveMovies(): Response<MovieResponse> {
        return apiRequests.getMoviesListApi()
    }

}