package com.binhnk.retrofitwithroom.data.repository

import com.binhnk.retrofitwithroom.data.model.UserCreated
import com.binhnk.retrofitwithroom.data.remote.UserApi
import com.binhnk.retrofitwithroom.data.remote.response.UserResponse
import io.reactivex.Observable
import retrofit2.Call

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {
    override fun getUsersUsingRx(page: Int?): Observable<UserResponse> {
        return userApi.getAllUsersUsingRx(page.toString())
    }

    override fun postUser(title: String, body: String): Observable<UserCreated> {
        return userApi.postUser(title, body)
    }
}