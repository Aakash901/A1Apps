package com.example.a1apps.viewmodel


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a1apps.repository.NetworkService
import com.example.a1apps.repository.model.MangaData
import com.example.a1apps.repository.model.Response
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

    fun loadData(context: Context) {
        if (isDataLoaded) {
            return
        }

        if (!isNetworkAvailable(context)) {
            _mangaList.postValue(emptyList())
            Toast.makeText(context, "No network connection", Toast.LENGTH_SHORT).show()
            return
        }
        _isLoading.postValue(true)
        _mangaList.postValue(emptyList())

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

    fun refreshData(context: Context) {
        isDataLoaded = false
        loadData(context)
    }

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
}