package com.binhnk.retrofitwithroom.data.repository

import com.binhnk.retrofitwithroom.data.model.UserCreated
import com.binhnk.retrofitwithroom.data.remote.response.UserResponse
import retrofit2.Call

interface UserRepository {
    fun getUsers(page: Int? = 0): Call<UserResponse>
    fun postUser( title: String,
                  body: String): Call<UserCreated>
}