package com.example.a1apps.repository.favouriteDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_manga")
data class FavoriteManga(
    @PrimaryKey val id: String,
    val title: String,
    val thumb: String,
    val summary: String,
    val authors: List<String>,
    val status: String,
    val genres: List<String>
)
