package com.binhnk.retrofitwithroom.ui.storage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.adapters.UserAdapter
import com.binhnk.retrofitwithroom.databinding.ActivityStorageBinding
import com.binhnk.retrofitwithroom.db.UserDatabase
import com.binhnk.retrofitwithroom.models.user.User

class StorageActivity : AppCompatActivity() {

    private var mViewModel: StorageBindingViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProviders.of(this).get(StorageBindingViewModel::class.java)
            .apply {
                this.userAdapter = UserAdapter(this@StorageActivity, object : UserAdapter.Callback {
                    override fun onItemClicked(mUserClicked: User) {
                        // do nothing
                    }

                    override fun onItemLongClicked(mUserClicked: User) {
                        // do nothing
                    }

                })
            }

        // binding
        DataBindingUtil.setContentView<ActivityStorageBinding>(
            this@StorageActivity,
            R.layout.activity_storage
        ).apply {
            this.lifecycleOwner = this@StorageActivity
            this.viewModel = mViewModel
            this.activity = this@StorageActivity
        }


        mViewModel!!.userListLiveData.observe(this, Observer {
            val data = if (it != null) {
                if (it.isEmpty()) {
                    mViewModel!!.tvNoDataVisibility.postValue(View.VISIBLE)
                    ArrayList()
                } else {
                    mViewModel!!.tvNoDataVisibility.postValue(View.GONE)
                    ArrayList(it)
                }
            } else {
                mViewModel!!.tvNoDataVisibility.postValue(View.VISIBLE)
                ArrayList()
            }
            Log.e("Ahihi", "1 ${data.size}")
            mViewModel!!.userAdapter!!.updateAdapter(data)
        })

        val userDBRoom = UserDatabase.getInstance(this@StorageActivity)
        Thread(Runnable {
            val data = userDBRoom.userDAO().getALlUser()
            Log.e("Ahihi", "2 ${data.size}")
            runOnUiThread { mViewModel!!.userListLiveData.value = data }
        }).start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_enter_ltr, R.anim.anim_exit_ltr)
    }
}