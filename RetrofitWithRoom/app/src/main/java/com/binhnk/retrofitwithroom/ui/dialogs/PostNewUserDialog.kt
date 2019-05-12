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

    private lateinit var edt_id: EditText
    private lateinit var edt_page: EditText
    private lateinit var edt_first_name: EditText
    private lateinit var edt_second_name: EditText
    private lateinit var edt_email: EditText

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
        edt_id = findViewById(R.id.edt_id)
        edt_page = findViewById(R.id.edt_page)
        edt_first_name = findViewById(R.id.edt_first_name)
        edt_second_name = findViewById(R.id.edt_second_name)
        edt_email = findViewById(R.id.edt_email)
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
            if (edt_first_name.text.toString() != ""
                && edt_second_name.text.toString() != ""
                && edt_email.text.toString() != ""
            ) {
                dismiss()
                mCallback.onSubmit(
                    edt_first_name.text.toString(),
                    edt_second_name.text.toString(),
                    edt_email.text.toString()
                )
            }
        }
    }

    interface Callback {
        fun onSubmit(firstName: String, secondName: String, email: String)
    }
}