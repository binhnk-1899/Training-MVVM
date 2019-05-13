package com.binhnk.retrofitwithroom.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import com.binhnk.retrofitwithroom.R

class PostNewUserDialog(
    context: Context,
    private val mCallback: Callback
) : Dialog(context) {

    private lateinit var edt_job: EditText
    private lateinit var edt_name: EditText

    private lateinit var tv_cancel: TextView
    private lateinit var tv_submit: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_post_new_user)

        initUI()
        declareUI()
        initAction()
    }

    private fun initUI() {
        edt_job = findViewById(R.id.edt_job)
        edt_name = findViewById(R.id.edt_name)
        tv_cancel = findViewById(R.id.tv_cancel)
        tv_submit = findViewById(R.id.tv_submit)
    }

    private fun declareUI() {

    }

    private fun initAction() {
        tv_cancel.setOnClickListener {
            dismiss()
        }
        tv_submit.setOnClickListener {
            if (edt_job.text.toString() != ""
                && edt_name.text.toString() != ""
            ) {
                dismiss()
                mCallback.onSubmit(
                    edt_name.text.toString(),
                    edt_job.text.toString()
                )
            }
        }
    }

    interface Callback {
        fun onSubmit(name: String, job: String)
    }
}