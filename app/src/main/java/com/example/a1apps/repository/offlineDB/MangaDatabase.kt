package com.example.a1apps.repository.offlineDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.a1apps.repository.favouriteDB.Converters

//database for storing the offline data
@Database(entities = [OfflineManga::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MangaDatabase : RoomDatabase() {
    abstract fun mangaOfflineDao(): MangaOfflineDao
}
