package com.binhnk.rxjavaexample.data.repository

import com.binhnk.rxjavaexample.data.remote.api.PostApi
import com.binhnk.rxjavaexample.models.Post
import io.reactivex.Single

class PostRepositoryImpl(private val postApi: PostApi): PostRepository {
    override fun getPosts(): Single<List<Post>> {
        return postApi.getPosts()
    }

}