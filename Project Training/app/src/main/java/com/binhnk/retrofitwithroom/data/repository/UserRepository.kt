package com.binhnk.retrofitwithroom.data.repository

import com.binhnk.retrofitwithroom.data.model.UserCreated
import com.binhnk.retrofitwithroom.data.remote.response.UserResponse
import io.reactivex.Observable
import retrofit2.Call

interface UserRepository {
    fun getUsersUsingRx(page: Int? = 0): Observable<UserResponse>
    fun postUser(title: String,
                 body: String): Observable<UserCreated>
}