package com.binhnk.retrofitwithroom.ui.screen.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.data.model.User
import com.binhnk.retrofitwithroom.ui.adapters.UserAdapter
import com.binhnk.retrofitwithroom.ui.base.BaseFragment
import com.binhnk.retrofitwithroom.ui.screen.main.dialog.PostNewUserDialog
import com.binhnk.retrofitwithroom.ui.screen.main.dialog.PostStateDialog
import com.binhnk.retrofitwithroom.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.sharedViewModel

class MainFragment :
    BaseFragment<com.binhnk.retrofitwithroom.databinding.FragmentMainBinding, MainViewModel>() {
    private lateinit var mContext: Context
    private val mOwner: LifecycleOwner by lazy { this@MainFragment }

    override val viewModel by sharedViewModel<MainViewModel>()
    override val layoutId: Int
        get() = R.layout.fragment_main

    companion object {
        fun getInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity!!.applicationContext
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.run {
            userAdapter =
                UserAdapter(mContext, viewModel.userDAO, true, object : UserAdapter.Callback {
                    override fun onItemClicked(mUserClicked: User) {

                    }

                    override fun onItemLongClicked(mUserClicked: User) {

                    }

                })

            usersLiveData.observe(mOwner, Observer {
                userAdapter?.updateAdapter(it)
                noDataVisible.postValue(it.isNullOrEmpty())
            })

            isRefreshLoading.observe(mOwner, Observer {
                swipe_refresh_layout.isRefreshing = it
            })

            noDataVisible.observe(mOwner, Observer {
                if (it) {
                    tv_no_data.visibility = View.VISIBLE
                } else {
                    tv_no_data.visibility = View.GONE
                }
            })
        }

        rv_user.adapter = viewModel.userAdapter
    }

}