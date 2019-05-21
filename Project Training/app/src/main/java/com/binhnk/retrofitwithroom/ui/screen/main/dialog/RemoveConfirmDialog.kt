package com.binhnk.retrofitwithroom.ui.screen.main.dialog

import android.os.Bundle
import android.view.View
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.base.BaseDialogFragment
import com.binhnk.retrofitwithroom.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.dialog_confirm_remove.*
import org.koin.androidx.viewmodel.ext.sharedViewModel

class RemoveConfirmDialog : BaseDialogFragment<com.binhnk.retrofitwithroom.databinding.DialogConfirmRemoveBinding>(){
    override val layoutId: Int
        get() = R.layout.dialog_confirm_remove

    private val mViewModel by sharedViewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.viewModel = mViewModel

        tv_cancel.setOnClickListener {
            dismiss()
        }

        tv_delete.setOnClickListener {
            dismiss()
            mViewModel.confirmDeleteUser.call()
        }
    }
}