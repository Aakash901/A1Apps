package com.example.a1apps.repository

import android.util.Log
import android.widget.Toast
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object NetworkService {

    private const val API_KEY = "b81b0e2d18msh451d7dcfaccb923p1b3eddjsn411f03c95b6b"
    private const val HOST = "mangaverse-api.p.rapidapi.com"

    private val client = OkHttpClient()

    fun fetchData(callback: (String?) -> Unit) {
        val url =
            "https://mangaverse-api.p.rapidapi.com/manga/fetch?page=1&genres=Harem%2CFantasy&nsfw=true&type=all"

        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("X-RapidAPI-Key", API_KEY)
            .addHeader("X-RapidAPI-Host", HOST)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val responseData = response.body?.string()
                callback(responseData)
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                Log.d("NetworkCheck","check "+e.toString())
                callback(null)
            }
        })
    }
}
