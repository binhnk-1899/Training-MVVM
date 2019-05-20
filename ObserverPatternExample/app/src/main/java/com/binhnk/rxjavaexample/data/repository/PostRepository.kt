package com.binhnk.rxjavaexample.data.repository

import com.binhnk.rxjavaexample.models.Post
import io.reactivex.Single

interface PostRepository {
    fun getPosts(): Single<List<Post>>
}