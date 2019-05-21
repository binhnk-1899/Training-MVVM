package com.binhnk.retrofitwithroom.ui.screen.main.dialog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.base.BaseDialogFragment
import com.binhnk.retrofitwithroom.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.dialog_post_new_user.*
import org.koin.androidx.viewmodel.ext.sharedViewModel

class PostNewUserDialog :
    BaseDialogFragment<com.binhnk.retrofitwithroom.databinding.DialogPostNewUserBinding>() {

    override val layoutId: Int
        get() = R.layout.dialog_post_new_user

    private val mViewModel by sharedViewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.viewModel = mViewModel

        mViewModel.apply {
            cancelClicked.observe(this@PostNewUserDialog, Observer {
                dismiss()
            })
        }
    }

    /**
     * handler when dialog dismiss or cancel
     */
    private fun handleOnDismiss() {
        edt_job.text = null
        edt_name.text = null
    }

    override fun onStop() {
        super.onStop()
        handleOnDismiss()
    }
}