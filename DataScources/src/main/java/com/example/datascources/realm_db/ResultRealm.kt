package com.example.datascources.realm_db

import io.realm.RealmObject

open class ResultRealm:RealmObject{


    var adult: Boolean = false
    lateinit var backdrop_path: String
  //  lateinit var genre_ids: List<Int>
    var id: Int = 0
    var media_type: String = ""
    var original_language: String = ""
    var original_title: String = ""
    var overview: String = ""
    var popularity: Double = 0.0
    var poster_path: String = ""
    var release_date: String = ""
    var title: String = ""
    var video: Boolean = false
    var vote_average: Double = 0.0
    var vote_count: Int = 0



    constructor(){

    }

    constructor(
        adult: Boolean,
        backdrop_path: String,
        id: Int,
        media_type: String,
        original_language: String,
        original_title: String,
        overview: String,
        popularity: Double,
        poster_path: String,
        release_date: String,
        title: String,
        video: Boolean,
        vote_average: Double,
        vote_count: Int
    ) : super() {
        this.adult = adult
        this.backdrop_path = backdrop_path
        this.id = id
        this.media_type = media_type
        this.original_language = original_language
        this.original_title = original_title
        this.overview = overview
        this.popularity = popularity
        this.poster_path = poster_path
        this.release_date = release_date
        this.title = title
        this.video = video
        this.vote_average = vote_average
        this.vote_count = vote_count
    }
}