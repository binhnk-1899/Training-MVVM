package com.binhnk.clean.architecture.data.di

import com.binhnk.clean.architecture.data.UserRepositoryImpl
import com.binhnk.clean.architecture.data.model.UserEntityMapper
import com.binhnk.clean.architecture.data.remote.api.UserApi
import com.binhnk.clean.architecture.domain.repository.UserRepository
import org.koin.dsl.module

//fun createPrefHelper(appPrefs: AppPrefs): PrefHelper = appPrefs
//
//fun createAppPrefs(context: Context): AppPrefs = AppPrefs(context)

fun createItemRepositoryImpl(userApi: UserApi): UserRepositoryImpl =
        UserRepositoryImpl(userApi, UserEntityMapper())

fun createItemRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository = userRepositoryImpl

val repositoryModules = module {
//    single { createAppPrefs(get()) }
//
    single { createItemRepositoryImpl(get()) }
//
    single { createItemRepository(get()) }
//
//    single { createPrefHelper(get()) }
}
