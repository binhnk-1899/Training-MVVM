package com.binhnk.rxjavaexample

import android.app.Application
import com.binhnk.rxjavaexample.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            appModules
        }
    }
}