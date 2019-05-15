package com.binhnk.retrofitwithroom.data.remote

import com.binhnk.retrofitwithroom.data.model.UserCreated
import com.binhnk.retrofitwithroom.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.*


interface APICallback {
    @GET("api/users")
    fun getAllUsers(@Query("page") page: String): Call<UserResponse>

    @POST("api/usersLiveData")
    @FormUrlEncoded
    fun postUser(
        @Field("name") title: String,
        @Field("job") body: String
    ): Call<UserCreated>
}