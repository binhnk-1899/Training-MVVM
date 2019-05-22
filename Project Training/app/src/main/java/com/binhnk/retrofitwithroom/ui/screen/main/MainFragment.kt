package com.binhnk.retrofitwithroom.ui.screen.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.base.BaseFragment
import com.binhnk.retrofitwithroom.data.model.User
import com.binhnk.retrofitwithroom.ui.adapter.UserAdapter
import com.binhnk.retrofitwithroom.ui.viewmodel.MainViewModel
import com.binhnk.retrofitwithroom.utils.Utils
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

        viewModel.apply {
            userClientAdapter =
                    UserAdapter(mContext, true, object : UserAdapter.Callback {
                        override fun onItemLongClicked(mUserClicked: User) {
                            Utils.shortToast(mContext, "${mUserClicked.addedInDB}")
                        }

                        override fun onItemClicked(mUserClicked: User) {
                            insertUserToRoomDB(mUserClicked)
                        }

                    })

            userClientList.observe(mOwner, Observer<ArrayList<User>> {
                noDataClient.postValue(userClientList.value.isNullOrEmpty())
                userClientAdapter?.updateAdapter(userClientList.value!!)
            })

            isRefreshLoading.observe(mOwner, Observer {
                swipe_refresh_layout.isRefreshing = it
            })

            currentPage.observe(mOwner, Observer {
                isRefreshLoading.postValue(true)
                loadUserApi()
                updateCurrentPageState(it)
            })

            noDataClient.observe(mOwner, Observer {
                if (it) {
                    tv_no_data.visibility = View.VISIBLE
                } else {
                    tv_no_data.visibility = View.GONE
                }
            })

            addUserToDBSuccess.observe(mOwner, Observer {
                Utils.shortToast(
                        mContext,
                        "User has been add to database"
                )
                updateStateOfUsers()
            })

            addUserToDBFailure.observe(mOwner, Observer {
                Utils.shortToast(
                        mContext,
                        "User has been exist in database"
                )
            })

            userDeleted.observe(mOwner, Observer {
                updateStateOfUsers()
            })
        }

        rv_user.adapter = viewModel.userClientAdapter
    }

    /**
     * update current page state
     */
    private fun updateCurrentPageState(page: Int) {
        when (page) {
            1 -> {
                btn_get_page_1.setBackgroundResource(R.drawable.bg_button_green)
                btn_get_page_2.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_3.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_4.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_all.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_1.setTextColor(Color.WHITE)
                btn_get_page_2.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_page_3.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_page_4.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_all.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
            }
            2 -> {
                btn_get_page_1.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_2.setBackgroundResource(R.drawable.bg_button_green)
                btn_get_page_3.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_4.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_all.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_1.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_page_2.setTextColor(Color.WHITE)
                btn_get_page_3.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_page_4.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_all.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
            }
            3 -> {
                btn_get_page_1.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_2.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_3.setBackgroundResource(R.drawable.bg_button_green)
                btn_get_page_4.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_all.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_1.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_page_2.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_page_3.setTextColor(Color.WHITE)
                btn_get_page_4.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_all.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
            }
            4 -> {
                btn_get_page_1.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_2.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_3.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_4.setBackgroundResource(R.drawable.bg_button_green)
                btn_get_all.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_1.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_page_2.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_page_3.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_page_4.setTextColor(Color.WHITE)
                btn_get_all.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
            }
            else -> {
                btn_get_page_1.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_2.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_3.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_page_4.setBackgroundResource(R.drawable.bg_button_gray)
                btn_get_all.setBackgroundResource(R.drawable.bg_button_green)
                btn_get_page_1.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_page_2.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_page_3.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_page_4.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                btn_get_all.setTextColor(Color.WHITE)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.isRefreshLoading.removeObservers(mOwner)
    }

}