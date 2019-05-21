package com.binhnk.retrofitwithroom

import android.app.Application
import com.binhnk.retrofitwithroom.di.networkModule
import com.binhnk.retrofitwithroom.di.repositoryModule
import com.binhnk.retrofitwithroom.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}