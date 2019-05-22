package com.binhnk.retrofitwithroom.ui.screen.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.data.model.User
import com.binhnk.retrofitwithroom.ui.adapter.UserAdapter
import com.binhnk.retrofitwithroom.base.BaseFragment
import com.binhnk.retrofitwithroom.ui.screen.main.dialog.UserInfoDialog
import com.binhnk.retrofitwithroom.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_storage.*
import org.koin.androidx.viewmodel.ext.sharedViewModel

class StorageFragment :
    BaseFragment<com.binhnk.retrofitwithroom.databinding.FragmentStorageBinding, MainViewModel>() {

    companion object {
        fun getInstance() = StorageFragment()
    }

    private val mContext: Context by lazy {
        activity!!.applicationContext
    }

    override val viewModel by sharedViewModel<MainViewModel>()
    override val layoutId: Int
        get() = R.layout.fragment_storage


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            userRoomAdapter = UserAdapter(mContext,  false, object : UserAdapter.Callback {
                override fun onItemLongClicked(mUserClicked: User) {

                }

                override fun onItemClicked(mUserClicked: User) {
                    userClicked.postValue(mUserClicked)
                    val mInfoDialog =
                        UserInfoDialog()
                    mInfoDialog.show(childFragmentManager, "info")
                }

            })

            userRoomList.observe(this@StorageFragment, Observer {
                if (it.isNullOrEmpty()) {
                    noDataRoom.postValue(true)
                } else {
                    noDataRoom.postValue(false)
                }

                if (userRoomAdapter != null) {
                    userRoomAdapter!!.updateAdapter(it)
                }
            })

            noDataRoom.observe(this@StorageFragment, Observer {
                tv_no_data_storage.visibility = if (it) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            })
        }

        rv_user_storage.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rv_user_storage.adapter = viewModel.userRoomAdapter

        Thread(Runnable {
            viewModel.queryAllUserUsingRx()
        }).start()
    }
}