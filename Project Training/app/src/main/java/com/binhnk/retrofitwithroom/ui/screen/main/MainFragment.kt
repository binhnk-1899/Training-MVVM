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
import com.binhnk.retrofitwithroom.ui.viewmodel.MainViewModel
import com.binhnk.retrofitwithroom.utils.Utils
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.sharedViewModel
import kotlin.math.absoluteValue

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

        viewModel.apply {
            userDaoAdapter =
                    UserAdapter(mContext, true, object : UserAdapter.Callback {
                        override fun onItemClicked(mUserClicked: User) {
                            addUser(mUserClicked)
                        }

                    })

            userClientList.observe(mOwner, Observer {
                userDaoAdapter?.updateAdapter(it)
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

            addUserToDBSuccess.observe(mOwner, Observer {
                if (it != -1) {
                    Utils.shortToast(
                            mContext,
                            "User has been add to database"
                    )
                    userDaoAdapter?.updateCheckingState(it.absoluteValue)
                }
            })

            addUserToDBFailure.observe(mOwner, Observer {
                Utils.shortToast(
                        mContext,
                        "User has been exist in database"
                )
            })
        }

        rv_user.adapter = viewModel.userDaoAdapter
    }

    override fun onPause() {
        super.onPause()
        viewModel.isRefreshLoading.removeObservers(mOwner)
    }

}