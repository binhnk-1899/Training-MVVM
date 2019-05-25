package com.binhnk.clean.architecture.data.remote.api

import com.binhnk.clean.architecture.data.remote.response.GetUserResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("api/users")
    fun getAllUsersUsingRx(@Query("page") page: String): Observable<GetUserResponse>
}