package com.example.a1apps.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.a1apps.repository.favouriteDB.FavoriteManga
import com.example.a1apps.repository.favouriteDB.FavoriteMangaDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {

    private val favoriteMangaDao: FavoriteMangaDao = MyApp.database.favoriteMangaDao()

    val allFavoriteManga: LiveData<List<FavoriteManga>> = favoriteMangaDao.getAllFavorites()


    //to delete the item
    fun delete(favoriteManga: FavoriteManga, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMangaDao.delete(favoriteManga)
            showToast(context, "Item deleted from favorites !!") // Show toast message
        }
    }

    // helper function to show toast message on the main/UI thread
    private fun showToast(context: Context, message: String) {
        // Switch to the main/UI thread to show the toast
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }}
