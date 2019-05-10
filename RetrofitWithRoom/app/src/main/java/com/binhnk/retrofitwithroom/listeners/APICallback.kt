package com.binhnk.retrofitwithroom.listeners

import com.binhnk.retrofitwithroom.models.user.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APICallback {
    @GET("api/users")
    fun getAllUsers(@Query("page") page: String): Call<UsersResponse>
}