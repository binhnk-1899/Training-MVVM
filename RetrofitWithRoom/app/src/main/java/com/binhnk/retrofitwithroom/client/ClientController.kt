package com.binhnk.retrofitwithroom.client

import com.binhnk.retrofitwithroom.models.user.UsersResponse
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientController {

    companion object {
        const val BASE_URL = "https://reqres.in/"

        private var mRetrofit: Retrofit? = null
    }

    fun requestGetListUser(mCallback: retrofit2.Callback<UsersResponse>) {
        if (mRetrofit == null) {
            val gson = GsonBuilder().setLenient().create()
            mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}