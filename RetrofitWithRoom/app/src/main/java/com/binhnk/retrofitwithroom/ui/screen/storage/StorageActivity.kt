package com.binhnk.retrofitwithroom.ui.screen.storage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.data.model.User
import com.binhnk.retrofitwithroom.databinding.ActivityStorageBinding
import com.binhnk.retrofitwithroom.ui.adapters.UserAdapter
import com.binhnk.retrofitwithroom.ui.base.BaseActivity
import com.binhnk.retrofitwithroom.ui.screen.storage.dialog.RemoveConfirmDialog
import com.binhnk.retrofitwithroom.ui.screen.storage.dialog.UserInfoDialog
import kotlinx.android.synthetic.main.activity_storage.*
import org.koin.androidx.viewmodel.ext.viewModel


class StorageActivity : BaseActivity<ActivityStorageBinding, StorageActivityViewModel>() {
    private lateinit var mContext: Context
    private lateinit var mOwner: LifecycleOwner

    private var mConfirmDialog: RemoveConfirmDialog? = null
    private var mInfoDialog: UserInfoDialog? = null

    private var mUserAdapter: UserAdapter? = null

    override val viewModel: StorageActivityViewModel by viewModel()

    override val layoutId: Int
        get() = R.layout.activity_storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this@StorageActivity
        mOwner = this@StorageActivity

        initRvAdapter()

        viewModel.apply {
            userList.observe(mOwner, Observer {
                val data = if (it != null) {
                    if (it.isEmpty()) {
                        noDataVisibility.postValue(true)
                        ArrayList()
                    } else {
                        noDataVisibility.postValue(false)
                        ArrayList(it)
                    }
                } else {
                    noDataVisibility.postValue(true)
                    ArrayList()
                }

                if (mUserAdapter != null) {
                    mUserAdapter!!.updateAdapter(data)
                }
            })

            noDataVisibility.observe(mOwner, Observer {
                tv_no_data_storage.visibility = if (it) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            })

            onDeletePressed.observe(mOwner, Observer {
                if (mInfoDialog != null) {
                    try {
                        mInfoDialog!!.dismiss()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                if (mConfirmDialog == null) {
                    mConfirmDialog =
                        RemoveConfirmDialog()
                }
                mConfirmDialog!!.show(supportFragmentManager, "CONFIRM")
            })

            onConfirmDeletePressed.observe(mOwner, Observer {
                if (mConfirmDialog != null) {
                    try {
                        mConfirmDialog!!.dismiss()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                removeUserFromDB()
            })

            onMenuPressed.observe(mOwner, Observer {
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val layoutPopup = inflater.inflate(R.layout.menu_storage, null, false)
                val pw = PopupWindow(
                    layoutPopup,
                    resources.getDimensionPixelSize(R.dimen._150sdp),
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    true
                )
                pw.showAsDropDown(
                    im_menu,
                    0,
                    -(resources.getDimensionPixelSize(R.dimen._10sdp))
                )
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
        mUserAdapter = UserAdapter(mContext,
            viewModel.userDAO,
            false,
            object : UserAdapter.Callback {
            override fun onItemClicked(mUserClicked: User) {
                viewModel.userClicked.postValue(mUserClicked)
                if (mInfoDialog == null) {
                    mInfoDialog =
                        UserInfoDialog()
                }
                mInfoDialog!!.show(supportFragmentManager, "INFO")
            }

            override fun onItemLongClicked(mUserClicked: User) {
                // do nothing
            }

        })
        rv_user_storage.adapter = mUserAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.anim_enter_ltr,
            R.anim.anim_exit_ltr
        )
    }
}