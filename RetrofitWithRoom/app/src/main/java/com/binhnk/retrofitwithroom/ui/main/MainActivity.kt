package com.binhnk.retrofitwithroom.ui.main

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.adapters.UserAdapter
import com.binhnk.retrofitwithroom.databinding.ActivityMainBinding
import com.binhnk.retrofitwithroom.databinding.DialogPostNewUserBinding
import com.binhnk.retrofitwithroom.db.UserDatabase
import com.binhnk.retrofitwithroom.models.user.User
import com.binhnk.retrofitwithroom.ui.storage.StorageActivity
import com.binhnk.retrofitwithroom.utils.Utils

class MainActivity : AppCompatActivity() {
    private lateinit var mContext: Context
    private lateinit var mOwner: LifecycleOwner

    private lateinit var mViewModel: SharedMainVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this@MainActivity
        mOwner = this@MainActivity

        mViewModel = ViewModelProviders.of(this).get(SharedMainVM::class.java)
            .apply {
                this.userAdapter = UserAdapter(mContext, object : UserAdapter.Callback {
                    override fun onItemLongClicked(mUserClicked: User) {

                    }

                    override fun onItemClicked(mUserClicked: User) {
                        val db = UserDatabase.getInstance(mContext)
                        Thread(Runnable {
                            if (db.userDAO().getUserByUserId(mUserClicked.id) == null) {
                                db.userDAO().insertUser(mUserClicked)
                                runOnUiThread {
                                    Utils.shortToast(
                                        mContext,
                                        "User has been add to database"
                                    )
                                }
                            } else {
                                runOnUiThread {
                                    Utils.shortToast(
                                        mContext,
                                        "User has been exist in database"
                                    )
                                }
                            }
                        }).start()
                    }
                })
            }

        // data binding
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).apply {
            this.lifecycleOwner = mOwner
            this.viewModel = mViewModel
        }

        mViewModel.isLoading.observe(mOwner, Observer<Boolean> {
            if (it != null && it) {
                mViewModel.loadUsers()
                mViewModel.isLoading.postValue(false)
            }
        })

        mViewModel.startStorageActivity.observe(mOwner, Observer<Boolean> {
            if (it != null && it) {
                val animBundle = ActivityOptions.makeCustomAnimation(
                    mContext,
                    R.anim.anim_enter_rtl,
                    R.anim.anim_exit_rtl
                ).toBundle()
                val storageIntent = Intent(mContext, StorageActivity::class.java)
                storageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(storageIntent, animBundle)
                mViewModel.startStorageActivity.postValue(false)
            }
        })

        mViewModel.usersLiveData.observe(mOwner, Observer<ArrayList<User>> {
            if (it.isNullOrEmpty()) {
                mViewModel.noDataVisible.postValue(View.VISIBLE)
                mViewModel.userAdapter!!.updateAdapter(ArrayList())
            } else {
                mViewModel.noDataVisible.postValue(View.GONE)
                mViewModel.userAdapter!!.updateAdapter(it)
            }
        })

        mViewModel.postNewUserClicked.observe(mOwner, Observer<Boolean> {
            if (it != null && it) {
                // show post dialog
                val mPostDialog = Dialog(mContext)
                let {
                    val sharedViewModel = ViewModelProviders.of(this).get(SharedMainVM::class.java)
                    val reviewBinding: DialogPostNewUserBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(mContext),
                        R.layout.dialog_post_new_user,
                        null,
                        false
                    )
                    mPostDialog.setContentView(reviewBinding.root)
                    reviewBinding.apply {
                        this.viewModel = sharedViewModel
                    }
                    mPostDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    mPostDialog.show()
                    mPostDialog.window!!.setLayout(
                        resources.getDimensionPixelSize(R.dimen._250sdp),
                        WindowManager.LayoutParams.WRAP_CONTENT
                    )
                }
                mViewModel.postNewUserClicked.postValue(false)
            }
        })
    }
}
