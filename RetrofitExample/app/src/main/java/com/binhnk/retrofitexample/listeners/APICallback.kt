package com.binhnk.retrofitexample.listeners

import com.binhnk.retrofitexample.models.user.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APICallback {
    @GET("api/users")
    fun getAllUsers(@Query("page") page: String): Call<UsersResponse>
}