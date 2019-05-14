package com.binhnk.retrofitwithroom.ui.main

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.adapters.UserAdapter
import com.binhnk.retrofitwithroom.databinding.ActivityMainBinding
import com.binhnk.retrofitwithroom.db.UserDatabase
import com.binhnk.retrofitwithroom.models.user.User
import com.binhnk.retrofitwithroom.ui.base.BaseActivity
import com.binhnk.retrofitwithroom.ui.storage.StorageActivity
import com.binhnk.retrofitwithroom.utils.Utils
import org.koin.androidx.viewmodel.ext.viewModel

class MainActivity : AppCompatActivity() {
//    BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    private lateinit var mContext: Context
    private lateinit var mOwner: LifecycleOwner

     var mViewModel: MainActivityViewModel? = null
//    override val viewModel: MainActivityViewModel by viewModel()

//    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this@MainActivity
        mOwner = this@MainActivity

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                this.viewModel = mViewModel
            }

        mViewModel?.apply {
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

//            viewModel.isRefreshLoading.observe(mOwner, Observer {
//                if (it != null && it) {
//                    viewModel.loadUsers()
//                }
//            })

            mViewModel?.startStorageActivity?.observe(mOwner, Observer {
                val animBundle = ActivityOptions.makeCustomAnimation(
                    mContext,
                    R.anim.anim_enter_rtl,
                    R.anim.anim_exit_rtl
                ).toBundle()
                val storageIntent = Intent(mContext, StorageActivity::class.java)
                storageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(storageIntent, animBundle)
            })

            mViewModel?.usersLiveData?.observe(mOwner, Observer<ArrayList<User>> {
                if (it.isNullOrEmpty()) {
                    mViewModel?.noDataVisible?.postValue(View.VISIBLE)
                    mViewModel?.userAdapter!!.updateAdapter(ArrayList())
                } else {
                    mViewModel?.noDataVisible?.postValue(View.GONE)
                    mViewModel?.userAdapter!!.updateAdapter(it)
                }
            })

            mViewModel?.postNewUserClicked?.observe(mOwner, Observer<Boolean> {
                if (it != null && it) {
                    // show post dialog
//                    val mPostDialog = Dialog(mContext)
//                    let {
//                        val sharedViewModel =
//                            ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
//                        val reviewBinding: DialogPostNewUserBinding = DataBindingUtil.inflate(
//                            LayoutInflater.from(mContext),
//                            R.layout.dialog_post_new_user,
//                            null,
//                            false
//                        )
//                        mPostDialog.setContentView(reviewBinding.root)
//                        reviewBinding.apply {
//                            this.viewModel = sharedViewModel
//                        }
//                        mPostDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                        mPostDialog.show()
//                        mPostDialog.window!!.setLayout(
//                            resources.getDimensionPixelSize(R.dimen._250sdp),
//                            WindowManager.LayoutParams.WRAP_CONTENT
//                        )
//                    }
//                    viewModel.postNewUserClicked.postValue(false)
                }
            })
        }

    }
}
