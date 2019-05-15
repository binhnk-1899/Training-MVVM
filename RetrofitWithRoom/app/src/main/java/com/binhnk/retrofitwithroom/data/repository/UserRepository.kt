package com.binhnk.retrofitwithroom.data.repository

import com.binhnk.retrofitwithroom.data.remote.response.UserResponse
import retrofit2.Call

interface UserRepository {
    fun searchItems(page: Int? = 0): Call<UserResponse>
}