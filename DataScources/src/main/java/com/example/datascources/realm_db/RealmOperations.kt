package com.example.datascources.realm_db

import android.util.Log
import com.example.modelsmodule.MovieResponse
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

class RealmOperations {


    suspend fun insertListIntoRealm(movieToDatabase: MovieResponse, db: Realm)  {

            val realmResults: RealmList<ResultRealm> = responseToRlms(movieToDatabase)
        try {

            db .executeTransactionAwait(Dispatchers.IO){

                val movies = MoviesRealm().apply {
                    page= movieToDatabase.page
                    results=realmResults
                    total_pages = movieToDatabase.total_pages
                    total_results = movieToDatabase.total_results
                }

                it.insertOrUpdate(movies)

            }
Log.e("inserts","it inserts")
        } catch (e: Exception) {
        Log.e("realm insert error",e.message.toString())
        }




        }

    private fun responseToRlms(movieToDatabase: MovieResponse): RealmList<ResultRealm> {

        val resultsInRealm: RealmList<ResultRealm> = RealmList()
        for (element in movieToDatabase.results) {
            resultsInRealm.add(
                ResultRealm(element.adult,element.backdrop_path
                    ,element.id
                    ,element.backdrop_path,element.original_language
                    ,element.original_title,element.overview
                    ,element.popularity,element.poster_path
                    ,element.original_title,element.original_title,
                    element.video,
                    element.vote_average,element.vote_count)
            )
        }
        return resultsInRealm
    }
}