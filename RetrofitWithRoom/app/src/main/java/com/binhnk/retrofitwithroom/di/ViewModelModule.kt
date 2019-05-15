package com.binhnk.retrofitwithroom.di

import com.binhnk.retrofitwithroom.ui.screen.main.MainActivityViewModel
import com.binhnk.retrofitwithroom.ui.screen.storage.StorageActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel(get(), get()) }
    viewModel { StorageActivityViewModel(get()) }
}