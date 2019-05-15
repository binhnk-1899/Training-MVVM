package com.binhnk.retrofitwithroom.di

import android.content.Context
import androidx.room.Room
import com.binhnk.retrofitwithroom.data.db.UserDatabase
import com.binhnk.retrofitwithroom.data.constants.Constants
import com.binhnk.retrofitwithroom.data.pref.AppPrefs
import com.binhnk.retrofitwithroom.data.pref.PrefHelper
import com.google.gson.Gson
import org.koin.dsl.module

val repositoryModule = module {
    single { createDatabaseName() }
    single { createAppDatabase(get(), get()) }
    single { createUserDao(get()) }
    single<PrefHelper> {
        AppPrefs(
            get(),
            get()
        )
    }
    single { Gson() }
}

fun createDatabaseName() = Constants.DATABASE_NAME

fun createAppDatabase(dbName: String, context: Context) =
    Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        dbName
    ).fallbackToDestructiveMigration().build()

fun createUserDao(db: UserDatabase) = db.userDAO()
