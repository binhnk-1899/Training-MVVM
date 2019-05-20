package com.binhnk.rxjavaexample.di

import com.binhnk.rxjavaexample.rx.AppSchedulerProvider
import com.binhnk.rxjavaexample.rx.SchedulerProvider
import org.koin.dsl.module

val schedulers = module {
    single { AppSchedulerProvider() as SchedulerProvider }
}

val appModules = listOf(schedulers, networkModule, repositoryModules, viewModelModule)