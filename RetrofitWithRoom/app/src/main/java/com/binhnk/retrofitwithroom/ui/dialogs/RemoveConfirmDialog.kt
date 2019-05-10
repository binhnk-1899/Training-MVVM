package com.binhnk.retrofitwithroom.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import com.binhnk.retrofitwithroom.R

class RemoveConfirmDialog(
    context: Context,
    private val mCallback: Callback
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_confirm_remove)

        findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
            dismiss()
        }
        findViewById<TextView>(R.id.tv_ok).setOnClickListener {
            dismiss()
            mCallback.onOkClicked()
        }
    }

    interface Callback {
        fun onOkClicked()
    }
}