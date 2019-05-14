package com.binhnk.retrofitwithroom.di

import com.binhnk.retrofitwithroom.ui.main.MainViewModel
import com.binhnk.retrofitwithroom.ui.storage.StorageBindingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{StorageBindingViewModel()}
    viewModel{MainViewModel()}
}