package com.binhnk.retrofitwithroom.di

import com.binhnk.retrofitwithroom.data.scheduler.AppSchedulerProvider
import com.binhnk.retrofitwithroom.data.scheduler.SchedulerProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single { androidApplication().resources }
    single<SchedulerProvider> { AppSchedulerProvider() }
}