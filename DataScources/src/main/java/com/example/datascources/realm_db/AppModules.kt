package com.example.datascources.realm_db

  import io.realm.annotations.RealmModule


@RealmModule(  classes = [MoviesRealm::class, ResultRealm::class ])
class AppModules {



}