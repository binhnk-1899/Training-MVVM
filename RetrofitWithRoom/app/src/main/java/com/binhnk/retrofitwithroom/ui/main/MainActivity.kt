package com.binhnk.retrofitwithroom.ui.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.adapters.UserAdapter
import com.binhnk.retrofitwithroom.databinding.ActivityMainBinding
import com.binhnk.retrofitwithroom.db.UserDatabase
import com.binhnk.retrofitwithroom.models.user.User

class MainActivity : AppCompatActivity() {
    private lateinit var mContext: Context

    private lateinit var mViewModel: MainBindingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this@MainActivity

        mViewModel = ViewModelProviders.of(this).get(MainBindingViewModel::class.java)
            .apply {
                this.userAdapter = UserAdapter(mContext, object : UserAdapter.Callback {
                    override fun onItemLongClicked(mUserClicked: User) {
                        mViewModel.removeUserFromDB(this@MainActivity, mUserClicked)
                    }

                    override fun onItemClicked(mUserClicked: User) {
                        mViewModel.addNewUserToDB(this@MainActivity, mUserClicked)
                    }
                })
            }

        // data binding
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = mViewModel
            this.activity = this@MainActivity
        }

        mViewModel.userLiveData.observe(this, Observer<ArrayList<User>> {
            mViewModel.userAdapter!!.updateAdapter(it!!)
        })
    }
}
