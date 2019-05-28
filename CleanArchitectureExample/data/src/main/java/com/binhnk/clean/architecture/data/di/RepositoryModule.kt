package com.binhnk.clean.architecture.data.di

import android.content.Context
import androidx.room.Room
import com.binhnk.clean.architecture.data.Constants
import com.binhnk.clean.architecture.data.UserRepositoryImpl
import com.binhnk.clean.architecture.data.dao.UserDAO
import com.binhnk.clean.architecture.data.local.db.UserDatabase
import com.binhnk.clean.architecture.data.model.UserEntityMapper
import com.binhnk.clean.architecture.data.remote.api.UserApi
import com.binhnk.clean.architecture.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModules = module {
    single { createItemRepositoryImpl(get()) }
    single { createItemRepository(get()) }
    single { createDatabaseName() }
    single { createAppDatabase(get(), get()) }
    single { createUserDao(get()) }
}

fun createItemRepositoryImpl(userApi: UserApi): UserRepositoryImpl =
    UserRepositoryImpl(userApi, UserEntityMapper())

fun createItemRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
    userRepositoryImpl

fun createDatabaseName() = Constants.DATABASE_NAME

fun createAppDatabase(dbName: String, context: Context) =
    Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        dbName
    ).fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

fun createUserDao(db: UserDatabase): UserDAO = db.userDAO()