package com.binhnk.retrofitwithroom.di

import com.binhnk.retrofitwithroom.ui.viewmodel.MainViewModel
import com.binhnk.retrofitwithroom.ui.screen.storage.StorageActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { StorageActivityViewModel(get()) }
}