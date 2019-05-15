package com.binhnk.retrofitwithroom.di

import com.binhnk.retrofitwithroom.ui.main.MainActivityViewModel
import com.binhnk.retrofitwithroom.ui.storage.StorageActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { StorageActivityViewModel(get()) }
}