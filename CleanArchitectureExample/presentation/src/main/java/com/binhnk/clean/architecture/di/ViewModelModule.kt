package com.binhnk.clean.architecture.di

import com.binhnk.clean.architecture.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
}