package com.binhnk.retrofitwithroom.data.repository

import com.binhnk.retrofitwithroom.data.model.UserCreated
import com.binhnk.retrofitwithroom.data.remote.UserApi
import com.binhnk.retrofitwithroom.data.remote.response.UserResponse
import retrofit2.Call

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {

    override fun getUsers(page: Int?): Call<UserResponse> {
        return userApi.getAllUsers(page.toString())
    }

    override fun postUser(title: String, body: String): Call<UserCreated> {
        return userApi.postUser(title, body)
    }
}