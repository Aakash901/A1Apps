package com.example.a1apps.repository.offlineDB

import android.content.Context
import androidx.room.Room

   //this class is create to ensure the single instance of database

object MangaOfflineDatabaseProvider {
    @Volatile
    private var instance: MangaDatabase? = null

    fun getDatabase(context: Context): MangaDatabase {
        return instance ?: synchronized(this) {
            val newInstance = Room.databaseBuilder(
                context.applicationContext,
                MangaDatabase::class.java,
                "manga_offline_database"
            ).build()
            instance = newInstance
            newInstance
        }
    }
}
