package com.binhnk.demogooglenews.listener

import com.binhnk.demogooglenews.models.Article
import com.binhnk.demogooglenews.models.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GerritAPI {
    @GET("top-headlines")
    fun loadArticles(@Query("sources") source: String,
                     @Query("apiKey") apiKey: String): Call<News>
}