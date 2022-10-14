package com.example.moviesooq

import android.app.Application
import com.example.datascources.realm_db.AppModules
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration

@HiltAndroidApp
class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init (this)
        val config = RealmConfiguration.Builder().schemaVersion(5).modules(
            AppModules()
        )
            .deleteRealmIfMigrationNeeded()
            .name("realm.db")
            .allowQueriesOnUiThread(false)
            .allowWritesOnUiThread(false)
            .build()

        Realm.setDefaultConfiguration(config)

    }
}