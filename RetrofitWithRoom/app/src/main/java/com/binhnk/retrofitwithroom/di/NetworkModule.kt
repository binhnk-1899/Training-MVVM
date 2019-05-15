package com.binhnk.retrofitwithroom.di

import android.content.Context
import com.binhnk.retrofitwithroom.BuildConfig
import com.binhnk.retrofitwithroom.data.constants.Constants
import com.binhnk.retrofitwithroom.data.remote.UserApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { createOkHttpCache(get()) }
    single { createOkHttpClient() }
    single { createWebService(get()) }
    single { createItemApi(get()) }
}


val gson: Gson = GsonBuilder().setLenient().create()

fun createOkHttpCache(context: Context): Cache =
    Cache(context.cacheDir, (10 * 1024 * 1024).toLong())

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }
        .build()
}

fun createWebService(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

fun createItemApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)
