package com.binhnk.retrofitwithroom.ui.storage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.adapters.UserAdapter
import com.binhnk.retrofitwithroom.databinding.ActivityStorageBinding
import com.binhnk.retrofitwithroom.models.user.User
import com.binhnk.retrofitwithroom.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_storage.*
import org.koin.androidx.viewmodel.ext.viewModel
import kotlin.math.min

class StorageActivity : BaseActivity<ActivityStorageBinding, StorageActivityViewModel>() {

    private lateinit var mOwner: LifecycleOwner

    override val viewModel: StorageActivityViewModel by viewModel()
    override val layoutId: Int
        get() = R.layout.activity_storage

    private var mUserAdapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mOwner = this@StorageActivity

        initRvAdapter()

        viewModel.apply {
            userList.observe(mOwner, Observer {
                val data = if (it != null) {
                    if (it.isEmpty()) {
                        noDataVisibility.postValue(View.VISIBLE)
                        ArrayList()
                    } else {
                        noDataVisibility.postValue(View.GONE)
                        ArrayList(it)
                    }
                } else {
                    noDataVisibility.postValue(View.VISIBLE)
                    ArrayList()
                }
                if (mUserAdapter != null) {
                    mUserAdapter!!.updateAdapter(data)
                }
            })

            noDataVisibility.observe(mOwner, Observer {
                tv_no_data_storage.visibility = it
            })

            onBackPressed.observe(mOwner, Observer {
                onBackPressed()
            })
        }

    }

    /**
     * init rv adapter
     */
    private fun initRvAdapter() {
        mUserAdapter = UserAdapter(this@StorageActivity, object : UserAdapter.Callback {
            override fun onItemClicked(mUserClicked: User) {
                viewModel.userClicked.postValue(mUserClicked)
                val mInfoDialog = UserInfoDialog()
                mInfoDialog.show(supportFragmentManager, "INFO")
            }

            override fun onItemLongClicked(mUserClicked: User) {

            }

        })
        rv_user_storage.adapter = mUserAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_enter_ltr, R.anim.anim_exit_ltr)
    }
}