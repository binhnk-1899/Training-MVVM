package com.binhnk.retrofitwithroom.data.repository

import com.binhnk.retrofitwithroom.data.remote.UserApi
import com.binhnk.retrofitwithroom.data.remote.response.UserResponse
import retrofit2.Call

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {
    override fun searchItems(page: Int?): Call<UserResponse> {
        return userApi.getAllUsers(page.toString())
    }

}