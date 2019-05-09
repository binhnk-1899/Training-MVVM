package com.binhnk.demogooglenews.controller

import com.binhnk.demogooglenews.listener.APIService
import com.binhnk.demogooglenews.models.News
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConnectionController {
    companion object {
        const val API_KEY = "6ad25cf8a93e44c281a78ce8d3fe62f1"
        const val BASE_URL = "https://newsapi.org/v2/"
    }

    fun start(mCallback: Callback<News>) {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val gerritAPI = retrofit.create(APIService::class.java)

        val call = gerritAPI.loadArticles( "google-news", API_KEY)
        call.enqueue(mCallback)
    }
}