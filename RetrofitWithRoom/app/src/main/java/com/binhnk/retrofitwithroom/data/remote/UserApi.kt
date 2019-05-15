package com.binhnk.retrofitwithroom.data.remote

import com.binhnk.retrofitwithroom.data.model.UserCreated
import com.binhnk.retrofitwithroom.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.*


interface UserApi {
    @GET("api/users")
    fun getAllUsers(@Query("page") page: String): Call<UserResponse>

    @GET("api/users")
    fun getUsers(@Query("page") page: String): UserResponse

    @POST("api/usersLiveData")
    @FormUrlEncoded
    fun postUser(
        @Field("name") title: String,
        @Field("job") body: String
    ): Call<UserCreated>
}