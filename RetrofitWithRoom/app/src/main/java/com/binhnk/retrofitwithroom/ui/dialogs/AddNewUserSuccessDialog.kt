package com.binhnk.retrofitwithroom.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.models.user.UserCreated

class AddNewUserSuccessDialog(context: Context,
                              private val userCreated: UserCreated) : Dialog(context) {

    private lateinit var tv_id: TextView
    private lateinit var tv_job: TextView
    private lateinit var tv_name: TextView
    private lateinit var tv_create_at: TextView
    private lateinit var tv_ok: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_add_new_user_success)

        initUI()
        declareUI()
        initAction()
    }

    private fun initUI() {
        tv_id = findViewById(R.id.tv_id)
        tv_job = findViewById(R.id.tv_job)
        tv_name = findViewById(R.id.tv_name)
        tv_create_at = findViewById(R.id.tv_create_at)
        tv_ok = findViewById(R.id.tv_ok)
    }

    private fun declareUI() {
        tv_id.text = String.format(context.getString(R.string.user_id), userCreated.id)
        tv_job.text = String.format(context.getString(R.string.job), userCreated.job)
        tv_name.text = String.format(context.getString(R.string.name), userCreated.name)
        tv_create_at.text = String.format(context.getString(R.string.created_at), userCreated.createdAt)
    }

    private fun initAction() {
        tv_ok.setOnClickListener {
            dismiss()
        }
    }
}