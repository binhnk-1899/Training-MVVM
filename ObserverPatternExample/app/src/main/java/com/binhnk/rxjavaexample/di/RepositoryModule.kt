package com.binhnk.rxjavaexample.di

import com.binhnk.rxjavaexample.data.remote.api.PostApi
import com.binhnk.rxjavaexample.data.repository.PostRepository
import com.binhnk.rxjavaexample.data.repository.PostRepositoryImpl
import org.koin.dsl.module

val repositoryModules = module {
    single { createPostRepository(get()) }
}

fun createPostRepository(postApi: PostApi): PostRepository = PostRepositoryImpl(postApi)