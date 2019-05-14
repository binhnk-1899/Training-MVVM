package com.binhnk.retrofitwithroom.ui.dialogs.postuser

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.databinding.DialogPostNewUserBinding
import com.binhnk.retrofitwithroom.ui.main.MainBindingViewModel


class PostNewUserDialog(
    context: Context,
    private val mViewModel: MainBindingViewModel
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        val reviewBinding: DialogPostNewUserBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_post_new_user,
            null,
            false
        )
        setContentView(reviewBinding.root)

        reviewBinding.apply {
            this.viewModel = mViewModel
            this.dialog = this@PostNewUserDialog
        }
    }
}