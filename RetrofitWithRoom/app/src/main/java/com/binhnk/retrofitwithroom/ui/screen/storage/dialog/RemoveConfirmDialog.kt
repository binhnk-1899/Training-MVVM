package com.binhnk.retrofitwithroom.ui.screen.storage.dialog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.ui.base.BaseDialogFragment
import com.binhnk.retrofitwithroom.ui.screen.storage.StorageActivityViewModel
import org.koin.androidx.viewmodel.ext.sharedViewModel

class RemoveConfirmDialog : BaseDialogFragment<com.binhnk.retrofitwithroom.databinding.DialogConfirmRemoveBinding>(){
    override val layoutId: Int
        get() = R.layout.dialog_confirm_remove

    private val mViewModel by sharedViewModel<StorageActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.viewModel = mViewModel

        mViewModel.onCancelConfirmDialogPressed.observe(this, Observer {
            dismiss()
        })
        mViewModel.onConfirmDeletePressed.observe(this, Observer {
            mViewModel.onCancelConfirmDialogPressed.call()
        })

    }
}