package com.example.a1apps.viewmodel

import android.app.Application
import androidx.room.Room
import com.example.a1apps.repository.favouriteDB.AppDatabase

class MyApp : Application() {

    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "my-database"
        ).build()
    }
}
