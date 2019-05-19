package com.binhnk.rxjavaexample.data.remote.api

import com.binhnk.rxjavaexample.models.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface PostApi {
    @GET("posts")
    fun getPosts(): Observable<List<Post>>
}