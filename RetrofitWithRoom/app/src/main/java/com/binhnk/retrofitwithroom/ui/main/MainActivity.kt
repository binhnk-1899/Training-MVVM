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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.binhnk.retrofitwithroom.BR
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.adapters.UserAdapter
import com.binhnk.retrofitwithroom.databinding.ActivityMainBinding
import com.binhnk.retrofitwithroom.databinding.DialogPostNewUserBinding
import com.binhnk.retrofitwithroom.db.UserDatabase
import com.binhnk.retrofitwithroom.models.user.User
import com.binhnk.retrofitwithroom.ui.base.BaseActivity
import com.binhnk.retrofitwithroom.ui.storage.StorageActivity
import com.binhnk.retrofitwithroom.utils.Utils
import org.koin.androidx.viewmodel.ext.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var mContext: Context
    private lateinit var mOwner: LifecycleOwner

    override val viewModel: MainViewModel by viewModel()

    override val layoutId: Int = R.layout.activity_main

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
        }

        // data binding
//        DataBindingUtil.setContentView<ActivityMainBinding>(
//            this,
//            R.layout.activity_main
//        ).apply {
//            setVariable(BR.viewModel, viewModel)
//            this.lifecycleOwner = mOwner
//            this.viewModel = viewModel
//        }

        viewModel.isLoading.observe(mOwner, Observer<Boolean> {
            if (it != null && it) {
                viewModel.loadUsers()
                viewModel.isLoading.postValue(false)
            }
        })

        viewModel.startStorageActivity.observe(mOwner, Observer<Boolean> {
            if (it != null && it) {
                val animBundle = ActivityOptions.makeCustomAnimation(
                    mContext,
                    R.anim.anim_enter_rtl,
                    R.anim.anim_exit_rtl
                ).toBundle()
                val storageIntent = Intent(mContext, StorageActivity::class.java)
                storageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(storageIntent, animBundle)
                viewModel.startStorageActivity.postValue(false)
            }
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
                // show post dialog
                val mPostDialog = Dialog(mContext)
                let {
                    val sharedViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
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
                viewModel.postNewUserClicked.postValue(false)
            }
        })
    }
}
