package com.example.datascources.realm_db

import io.realm.RealmList
import io.realm.RealmObject

open class MoviesRealm : RealmObject {
    var page: Int = 0
    lateinit var results: RealmList<ResultRealm>
    var total_pages: Int = 0
    var total_results: Int = 0


    constructor(){}
    constructor(
        page: Int,
        results: RealmList<ResultRealm>,
        total_pages: Int,
        total_results: Int
    ) : super() {
        this.page = page
        this.results = results
        this.total_pages = total_pages
        this.total_results = total_results
    }

}