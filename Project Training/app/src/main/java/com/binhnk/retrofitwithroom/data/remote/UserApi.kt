package com.binhnk.retrofitwithroom.data.remote

import com.binhnk.retrofitwithroom.data.model.UserCreated
import com.binhnk.retrofitwithroom.data.remote.response.UserResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*


interface UserApi {
    @GET("api/users")
    fun getAllUsers(@Query("page") page: String): Call<UserResponse>

    @GET("api/users")
    fun getAllUsersUsingRx(@Query("page") page: String): Observable<UserResponse>

    @POST("api/userClientList")
    @FormUrlEncoded
    fun postUser(
        @Field("name") title: String,
        @Field("job") body: String
    ): Call<UserCreated>
}