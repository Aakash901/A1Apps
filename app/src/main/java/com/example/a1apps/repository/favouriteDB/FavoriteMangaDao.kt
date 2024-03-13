package com.example.a1apps.repository.favouriteDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavoriteMangaDao {

    // add ane item in db
    @Insert
    fun insert(favoriteManga: FavoriteManga)

    //delete item from db
    @Delete
    fun delete(favoriteManga: FavoriteManga)

    //show item from db
    @Query("SELECT * FROM favorite_manga")
    fun getAllFavorites(): LiveData<List<FavoriteManga>>
}
