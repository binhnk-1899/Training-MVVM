package com.binhnk.retrofitwithroom.ui.screen.main.dialog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.databinding.DialogUserInfoBinding
import com.binhnk.retrofitwithroom.ui.base.BaseDialogFragment
import com.binhnk.retrofitwithroom.ui.viewmodel.MainViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_user_info.*
import org.koin.androidx.viewmodel.ext.sharedViewModel

class UserInfoDialog : BaseDialogFragment<DialogUserInfoBinding>() {
    override val layoutId: Int
        get() = R.layout.dialog_user_info

    private val mViewModel by sharedViewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.viewModel = mViewModel

//        mViewModel.onCancelPressed.observe(this, Observer {
//            dismiss()
//        })
//
//        mViewModel.onDeletePressed.observe(this, Observer {
//            Log.e("Ahihi", "Delete pressed")
//            mViewModel.onCancelPressed.call()
//        })

        mViewModel.userClicked.observe(this, Observer {
            if (it != null) {
                Glide.with(context!!)
                    .load(it.avatar)
                    .into(im_avatar)
                tv_id_2.text = it.id.toString()
                tv_first_name_2.text = it.firstName
                tv_last_name_2.text = it.lastName
                tv_email_2.text = it.email
            }
        })

        tv_ok.setOnClickListener {
            dismiss()
        }

        tv_delete_user.setOnClickListener {
            dismiss()
            mViewModel.deleteUser.call()
        }
    }
}