//package com.binhnk.retrofitwithroom.ui.dialogs.postuser
//
//import android.app.Dialog
//import android.content.Context
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.Window
//import androidx.databinding.DataBindingUtil
//import com.binhnk.retrofitwithroom.R
//import com.binhnk.retrofitwithroom.databinding.DialogAddNewUserSuccessBinding
//import com.binhnk.retrofitwithroom.models.user.UserCreated
//import com.binhnk.retrofitwithroom.ui.main.MainActivityViewModel
//
//class PostNewUserSuccessDialog(
//    context: Context,
//    private val userCreated: UserCreated,
//    private val mViewModel: MainActivityViewModel
//) : Dialog(context) {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        val reviewBinding: DialogAddNewUserSuccessBinding = DataBindingUtil.inflate(
//            LayoutInflater.from(context),
//            R.layout.dialog_add_new_user_success,
//            null,
//            false
//        )
//        setContentView(reviewBinding.root)
//
//
//    }
//
//}