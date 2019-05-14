package com.binhnk.retrofitwithroom.ui.dialogs.postuser

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.ui.base.BaseDialogFragment
import com.binhnk.retrofitwithroom.ui.main.MainActivityViewModel
import kotlinx.android.synthetic.main.dialog_post_new_user.*
import org.koin.androidx.viewmodel.ext.sharedViewModel

class PostNewUserDialog :
    BaseDialogFragment<com.binhnk.retrofitwithroom.databinding.DialogPostNewUserBinding>() {

    override val layoutId: Int
        get() = R.layout.dialog_post_new_user

    private val mViewModel by sharedViewModel<MainActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.viewModel = mViewModel

//        viewBinding.viewModel?.userPost?.observe(this.viewLifecycleOwner, Observer {
//            edt_name.setText(it)
//        })
    }
}