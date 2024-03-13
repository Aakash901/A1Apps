package com.example.a1apps.repository.favouriteDB

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromGenresList(genres: List<String>): String {
        return genres.joinToString(",")
    }

    @TypeConverter
    fun toGenresList(genresString: String): List<String> {
        return genresString.split(",")
    }
}
