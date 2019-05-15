package com.binhnk.retrofitwithroom.ui.screen.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.databinding.DialogPostSuccessBinding
import com.binhnk.retrofitwithroom.ui.base.BaseDialogFragment
import org.koin.androidx.viewmodel.ext.sharedViewModel

class PostNewUserSuccessDialog : BaseDialogFragment<DialogPostSuccessBinding>() {
    override val layoutId: Int
        get() = R.layout.dialog_post_success

    private val mViewModel by sharedViewModel<MainActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.viewModel = mViewModel

        mViewModel.apply {
            cancelClicked.observe(this@PostNewUserSuccessDialog, Observer {
                dismiss()
                userCreated.postValue(null)
            })
        }
    }
}