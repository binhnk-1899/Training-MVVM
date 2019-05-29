package com.binhnk.retrofitwithroom.ui.screen.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.base.BaseActivity
import com.binhnk.retrofitwithroom.ui.screen.main.dialog.PostNewUserDialog
import com.binhnk.retrofitwithroom.ui.screen.main.dialog.PostStateDialog
import com.binhnk.retrofitwithroom.ui.screen.main.dialog.RemoveConfirmDialog
import com.binhnk.retrofitwithroom.ui.viewmodel.MainViewModel
import com.binhnk.retrofitwithroom.utils.Utils
import kotlinx.android.synthetic.main.toolbar_main.*
import org.koin.android.ext.android.inject


class MainActivity :
    BaseActivity<com.binhnk.retrofitwithroom.databinding.ActivityMainBinding, MainViewModel>() {

    private lateinit var mContext: Context
    private lateinit var mOwner: LifecycleOwner

    override val viewModel: MainViewModel by inject()
    override val layoutId: Int
        get() = com.binhnk.retrofitwithroom.R.layout.activity_main

    private var mPostNewUserDialog: PostNewUserDialog? = null
    private var mPostStateDialog: PostStateDialog? = null

    private val mainFragment: MainFragment by lazy { MainFragment.getInstance() }
    private val storageFragment: StorageFragment by lazy { StorageFragment.getInstance() }

    /**
     * transition for toolbar
     */
    private val toolbarConstraint1 = ConstraintSet()
    private val toolbarConstraint2 = ConstraintSet()
    private val autoTransition: AutoTransition by lazy {
        AutoTransition()
    }.apply {
        this.value.duration = 350
    }


    private var mRemoveConfirmDialog: RemoveConfirmDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this@MainActivity
        mOwner = this@MainActivity

        supportFragmentManager.beginTransaction()
            .add(com.binhnk.retrofitwithroom.R.id.container, mainFragment)
            .commit()

        viewModel.apply {

            backClicked.observe(mOwner, Observer {
                onBackPressed()
            })

            goToStorage.observe(mOwner, Observer {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        com.binhnk.retrofitwithroom.R.anim.anim_enter_rtl,
                        com.binhnk.retrofitwithroom.R.anim.anim_exit_rtl,
                        com.binhnk.retrofitwithroom.R.anim.anim_enter_ltr,
                        com.binhnk.retrofitwithroom.R.anim.anim_exit_ltr
                    )
                    .add(com.binhnk.retrofitwithroom.R.id.container, storageFragment)
                    .addToBackStack("storage")
                    .hide(mainFragment)
                    .commitAllowingStateLoss()

                Handler().post {
                    TransitionManager.beginDelayedTransition(root, autoTransition)
                    toolbarConstraint2.applyTo(root)
                    textView.text = "User storage"
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

            deleteUser.observe(mOwner, Observer {
                if (mRemoveConfirmDialog == null) {
                    mRemoveConfirmDialog =
                        RemoveConfirmDialog()
                }
                mRemoveConfirmDialog!!.show(supportFragmentManager, "REMOVE_CONFIRM")
            })

            confirmDeleteUser.observe(mOwner, Observer {
                deleteUser()
            })

            getAll.observe(mOwner, Observer {
                Utils.shortToast(mContext, "Get all")
            })
        }

        toolbarConstraint1.clone(mContext, com.binhnk.retrofitwithroom.R.layout.toolbar_main)
        toolbarConstraint2.clone(mContext, com.binhnk.retrofitwithroom.R.layout.toolbar_main_alt)

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            Handler().post {
                TransitionManager.beginDelayedTransition(root, autoTransition)
                toolbarConstraint1.applyTo(root)
                textView.text = getString(com.binhnk.retrofitwithroom.R.string.app_name)
            }
        }
        super.onBackPressed()
    }
}
