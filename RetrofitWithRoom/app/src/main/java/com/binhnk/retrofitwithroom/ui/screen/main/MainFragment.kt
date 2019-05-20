package com.binhnk.retrofitwithroom.ui.screen.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.databinding.ActivityMainBinding
import com.binhnk.retrofitwithroom.ui.base.BaseFragment
import com.binhnk.retrofitwithroom.ui.screen.main.dialog.PostNewUserDialog
import com.binhnk.retrofitwithroom.ui.screen.main.dialog.PostStateDialog
import org.koin.androidx.viewmodel.ext.viewModel

class MainFragment : BaseFragment<ActivityMainBinding, MainActivityViewModel>() {
    private lateinit var mContext: Context
    private val mOwner: LifecycleOwner by lazy { this@MainFragment }

    override val viewModel: MainActivityViewModel by viewModel()
    override val layoutId: Int
        get() = R.layout.fragment_main

    companion object {
        fun getInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity!!.applicationContext
    }

    private var mPostNewUserDialog: PostNewUserDialog? = null
    private var mPostStateDialog: PostStateDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.run {

        }
    }

}