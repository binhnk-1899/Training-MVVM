package com.binhnk.retrofitwithroom.data.remote

import com.binhnk.retrofitwithroom.data.model.UserCreated
import com.binhnk.retrofitwithroom.data.remote.response.UserResponse
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    companion object {
        const val BASE_URL = "https://reqres.in/"

        private var mRetrofit: Retrofit? = null

        fun requestGetListUser(page: Int, mCallback: retrofit2.Callback<UserResponse>) {
            if (mRetrofit == null) {
                val gson = GsonBuilder().setLenient().create()
                mRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }

            val call = mRetrofit!!.create(UserApi::class.java)
                .getAllUsers("$page")
            call.enqueue(mCallback)
        }

        fun postUser(
            name: String,
            job: String,
            mCallback: retrofit2.Callback<UserCreated>
        ) {
            if (mRetrofit == null) {
                val gson = GsonBuilder().setLenient().create()
                mRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }

            val call = mRetrofit!!.create(UserApi::class.java)
                .postUser(
                    name,
                    job
                )
            call.enqueue(mCallback)
        }
    }
}