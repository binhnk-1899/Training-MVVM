package com.binhnk.retrofitwithroom.ui.screen.main

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.data.model.User
import com.binhnk.retrofitwithroom.databinding.ActivityMainBinding
import com.binhnk.retrofitwithroom.ui.adapters.UserAdapter
import com.binhnk.retrofitwithroom.ui.base.BaseActivity
import com.binhnk.retrofitwithroom.ui.screen.main.dialog.PostNewUserDialog
import com.binhnk.retrofitwithroom.ui.screen.main.dialog.PostStateDialog
import com.binhnk.retrofitwithroom.ui.screen.storage.StorageActivity
import com.binhnk.retrofitwithroom.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.viewModel
import kotlin.math.absoluteValue

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    private lateinit var mContext: Context
    private lateinit var mOwner: LifecycleOwner

    override val viewModel: MainActivityViewModel by viewModel()
    override val layoutId: Int
        get() = R.layout.activity_main

    private var mPostNewUserDialog: PostNewUserDialog? = null
    private var mPostStateDialog: PostStateDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this@MainActivity
        mOwner = this@MainActivity

        viewModel.apply {
            userAdapter = UserAdapter(mContext,
                viewModel.userDAO,
                true,
                object : UserAdapter.Callback {
                    override fun onItemLongClicked(mUserClicked: User) {
                        // do nothing
                    }

                    override fun onItemClicked(mUserClicked: User) {
                        addUserToDB(mUserClicked)
                    }
                })

            addUserToDBSuccess.observe(mOwner, Observer {
                if (it != -1) {
                    Utils.shortToast(
                        mContext,
                        "User has been add to database"
                    )
                    userAdapter?.updateCheckingState(it.absoluteValue)
                }
            })

            addUserToDBFailure.observe(mOwner, Observer {
                Utils.shortToast(
                    mContext,
                    "User has been exist in database"
                )
            })

            startStorageActivity.observe(mOwner, Observer {
                val animBundle = ActivityOptions.makeCustomAnimation(
                    mContext,
                    R.anim.anim_enter_rtl,
                    R.anim.anim_exit_rtl
                ).toBundle()
                val storageIntent = Intent(mContext, StorageActivity::class.java)
                storageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(storageIntent, animBundle)
            })

            usersLiveData.observe(mOwner, Observer<ArrayList<User>> {
                if (it.isNullOrEmpty()) {
                    viewModel.noDataVisible.postValue(true)
                    viewModel.userAdapter!!.updateAdapter(ArrayList())
                } else {
                    viewModel.noDataVisible.postValue(false)
                    viewModel.userAdapter!!.updateAdapter(it)
                }
            })

            postNewUserClicked.observe(mOwner, Observer<Boolean> {
                if (it != null && it) {
                    if (mPostNewUserDialog == null) {
                        mPostNewUserDialog = PostNewUserDialog()
                    }
                    mPostNewUserDialog!!.show(supportFragmentManager, "POST")
                }
                viewModel.postNewUserClicked.postValue(false)
            })

            noDataVisible.observe(mOwner, Observer {
                if (it) {
                    tv_no_data.visibility = View.VISIBLE
                } else {
                    tv_no_data.visibility = View.GONE
                }
            })

            postClicked.observe(mOwner, Observer {
                if (mPostNewUserDialog != null) {
                    mPostNewUserDialog!!.dismiss()
                }
                if (mPostStateDialog == null) {
                    mPostStateDialog =
                        PostStateDialog()
                }
                mPostStateDialog!!.show(supportFragmentManager, "POSTING")
            })
        }

    }
}
