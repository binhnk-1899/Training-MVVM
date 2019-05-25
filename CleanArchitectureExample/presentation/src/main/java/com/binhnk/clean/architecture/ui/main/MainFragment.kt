package com.binhnk.clean.architecture.ui.main

import android.os.Bundle
import android.view.View
import com.binhnk.clean.architecture.R
import com.binhnk.clean.architecture.base.BaseFragment
import org.koin.androidx.viewmodel.ext.sharedViewModel

class MainFragment: BaseFragment<com.binhnk.clean.architecture.databinding.FragmentMainBinding, MainViewModel>() {
    override val viewModel: MainViewModel by sharedViewModel()
    override val layoutId: Int
        get() = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}