package com.binhnk.rxjavaexample.data.client

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
        private var INSTANCE: Retrofit? = null

        fun getInstance(): Retrofit {
            if (INSTANCE == null) {
                val gson = GsonBuilder().setLenient().create()
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return INSTANCE!!
        }
    }
}