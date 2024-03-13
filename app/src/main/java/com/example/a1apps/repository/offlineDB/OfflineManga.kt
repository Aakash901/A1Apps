package com.example.a1apps.repository.offlineDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offline_manga")
data class OfflineManga(
    @PrimaryKey val id: String,
    val title: String,
    val thumb: String,
    val summary: String,
    val authors: List<String>,
    val status: String,
    val genres: List<String>
)
