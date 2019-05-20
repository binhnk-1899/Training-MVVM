package com.binhnk.rxjavaexample.di

import com.binhnk.rxjavaexample.ui.rxjava.PostViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PostViewModel(get(), get()) }
}