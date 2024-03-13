package com.example.a1apps.repository.favouriteDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//favourite database

@Database(entities = [FavoriteManga::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMangaDao(): FavoriteMangaDao
}
