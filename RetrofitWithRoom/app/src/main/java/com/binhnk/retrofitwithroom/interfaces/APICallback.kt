package com.binhnk.retrofitwithroom.interfaces

import com.binhnk.retrofitwithroom.models.user.User
import com.binhnk.retrofitwithroom.models.user.UserCreated
import com.binhnk.retrofitwithroom.models.user.UserResponse
import retrofit2.Call
import retrofit2.http.*


interface APICallback {
    @GET("api/users")
    fun getAllUsers(@Query("page") page: String): Call<UserResponse>

    @POST("/posts")
    @FormUrlEncoded
    fun savePost(
        @Field("name") title: String,
        @Field("job") body: String,
        @Field("id") userId: Long
    ): Call<UserCreated>
}