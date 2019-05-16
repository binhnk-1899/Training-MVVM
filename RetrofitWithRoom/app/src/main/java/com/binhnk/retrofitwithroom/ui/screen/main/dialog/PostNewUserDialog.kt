package com.binhnk.retrofitwithroom.ui.screen.main.dialog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.ui.base.BaseDialogFragment
import com.binhnk.retrofitwithroom.ui.screen.main.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.sharedViewModel

class PostNewUserDialog :
    BaseDialogFragment<com.binhnk.retrofitwithroom.databinding.DialogPostNewUserBinding>() {

    override val layoutId: Int
        get() = R.layout.dialog_post_new_user

    private val mViewModel by sharedViewModel<MainActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.viewModel = mViewModel

        mViewModel.apply {
            cancelClicked.observe(this@PostNewUserDialog, Observer {
                dismiss()
            })
            postClicked.observe(this@PostNewUserDialog, Observer {
                dismiss()
            })
        }
    }
}