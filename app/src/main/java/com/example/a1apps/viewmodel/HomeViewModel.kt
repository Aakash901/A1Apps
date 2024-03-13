package com.example.a1apps.viewmodel


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1apps.repository.NetworkService
import com.example.a1apps.repository.model.MangaData
import com.example.a1apps.repository.model.Response
import com.example.a1apps.repository.offlineDB.MangaOfflineDao
import com.example.a1apps.repository.offlineDB.MangaOfflineDatabaseProvider
import com.example.a1apps.repository.offlineDB.OfflineManga
import com.example.a1apps.repository.favouriteDB.FavoriteManga
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _mangaList = MutableLiveData<List<MangaData>>()
    val mangaList: LiveData<List<MangaData>> = _mangaList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var isDataLoaded = false

    // Inject FavoriteMangaDao dependency through constructor
    private val favoriteMangaDao = MyApp.database.favoriteMangaDao()

    private lateinit var mangaOfflineDao: MangaOfflineDao

    // Initialize the DAO
    fun init(context: Context) {
        mangaOfflineDao = MangaOfflineDatabaseProvider.getDatabase(context).mangaOfflineDao()
    }

    // Function to insert a list of offline manga data into the local database
    fun insertAllOfflineManga(offlineMangaList: List<OfflineManga>) {
        viewModelScope.launch(Dispatchers.IO) {
            mangaOfflineDao.insertAllFavoriteManga(offlineMangaList)
        }
    }

    // Function to get all offline manga data from the local database
    fun getAllOfflineManga(context: Context): List<OfflineManga> {
        return mangaOfflineDao.getAllFavoriteManga()
    }


    fun loadData(context: Context) {
        if (isDataLoaded) {
            return
        }

        if (!isNetworkAvailable(context)) {
            viewModelScope.launch(Dispatchers.IO) {
                // Geting all offline manga data from the local database
                val offlineMangaList = getAllOfflineManga(context)

                // Post the offline manga list to the LiveData
                _mangaList.postValue(offlineMangaList.map { offlineManga ->
                    MangaData(
                        id = offlineManga.id,
                        title = offlineManga.title,
                        thumb = offlineManga.thumb,
                        summary = offlineManga.summary,
                        authors = offlineManga.authors,
                        status = offlineManga.status,
                        genres = offlineManga.genres
                    )
                })
            }
            Toast.makeText(
                context,
                "No network connection. Showing offline data.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        _isLoading.postValue(true)
        _mangaList.postValue(emptyList())

        // network call to get the data from server
        GlobalScope.launch(Dispatchers.IO) {
            NetworkService.fetchData { responseData ->
                val gson = Gson()
                val response = gson.fromJson(responseData, Response::class.java)
                val mangaList = response?.data
                mangaList?.let {
                    _mangaList.postValue(it)
                    isDataLoaded = true
                }
                _isLoading.postValue(false)
            }
        }
    }

    //refreshing the data if data is no loaded
    fun refreshData(context: Context) {
        isDataLoaded = false
        loadData(context)
    }

    //here im trying to check network of device
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }

    //to add item favourite section
    fun insertFavoriteManga(favoriteManga: FavoriteManga, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoriteMangaDao.insert(favoriteManga)
                showToastOnMainThread(context, "Added to favorites !!")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error inserting favorite manga: ${e.message}")
                showToastOnMainThread(context, "Already added !!")
            }
        }
    }

    //to show message to the user ,showing it on main thread to avoid the other changes in ui
    private fun showToastOnMainThread(context: Context, message: String) {
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }



}