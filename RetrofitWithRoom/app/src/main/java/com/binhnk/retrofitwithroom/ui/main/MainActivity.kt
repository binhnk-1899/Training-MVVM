package com.binhnk.retrofitwithroom.ui.main

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.adapters.UserAdapter
import com.binhnk.retrofitwithroom.databinding.ActivityMainBinding
import com.binhnk.retrofitwithroom.db.UserDatabase
import com.binhnk.retrofitwithroom.models.user.User
import com.binhnk.retrofitwithroom.ui.base.BaseActivity
import com.binhnk.retrofitwithroom.ui.dialogs.postuser.PostNewUserDialog
import com.binhnk.retrofitwithroom.ui.storage.StorageActivity
import com.binhnk.retrofitwithroom.utils.Utils
import org.koin.androidx.viewmodel.ext.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    private lateinit var mContext: Context
    private lateinit var mOwner: LifecycleOwner

    override val viewModel: MainActivityViewModel by viewModel()
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this@MainActivity
        mOwner = this@MainActivity

        viewModel.apply {
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

            viewModel.startStorageActivity.observe(mOwner, Observer {
                val animBundle = ActivityOptions.makeCustomAnimation(
                    mContext,
                    R.anim.anim_enter_rtl,
                    R.anim.anim_exit_rtl
                ).toBundle()
                val storageIntent = Intent(mContext, StorageActivity::class.java)
                storageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(storageIntent, animBundle)
            })

            viewModel.usersLiveData.observe(mOwner, Observer<ArrayList<User>> {
                if (it.isNullOrEmpty()) {
                    viewModel.noDataVisible.postValue(View.VISIBLE)
                    viewModel.userAdapter!!.updateAdapter(ArrayList())
                } else {
                    viewModel.noDataVisible.postValue(View.GONE)
                    viewModel.userAdapter!!.updateAdapter(it)
                }
            })

            viewModel.postNewUserClicked.observe(mOwner, Observer<Boolean> {
                if (it != null && it) {
                    val mPostNewUserDialog = PostNewUserDialog()
                    mPostNewUserDialog.show(supportFragmentManager, "POST")

                }
                viewModel.postNewUserClicked.postValue(false)
            })
        }

    }
}
