package com.binhnk.retrofitwithroom.ui.screen.main.dialog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.data.constants.Constants
import com.binhnk.retrofitwithroom.data.model.UserCreated
import com.binhnk.retrofitwithroom.databinding.DialogPostSuccessBinding
import com.binhnk.retrofitwithroom.ui.base.BaseDialogFragment
import com.binhnk.retrofitwithroom.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.dialog_post_success.*
import org.koin.androidx.viewmodel.ext.sharedViewModel

class PostStateDialog : BaseDialogFragment<DialogPostSuccessBinding>() {
    override val layoutId: Int
        get() = R.layout.dialog_post_success

    private val mViewModel by sharedViewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.viewModel = mViewModel

        mViewModel.apply {
            cancelClicked.observe(this@PostStateDialog, Observer {
                dismiss()
                userCreated.postValue(null)
            })

            postUserFailure.observe(this@PostStateDialog, Observer {
                changeState(Constants.STATE_CREATE_FAILED)
            })
            postUserSuccess.observe(this@PostStateDialog, Observer {
                changeState(Constants.STATE_CREATE_SUCCESS)
            })
            postUserUnSuccess.observe(this@PostStateDialog, Observer {
                changeState(Constants.STATE_CREATE_FAILED)
            })

            userCreated.observe(this@PostStateDialog, Observer<UserCreated> {
                if (it != null) {
                    tv_id_2.text = it.id
                    tv_name_2.text = it.name
                    tv_job_2.text = it.job
                    tv_created_at_2.text = it.createdAt
                }
            })
        }
    }

    /**
     * change state
     */
    fun changeState(state: Int) {
        when (state) {
            Constants.STATE_CREATE_FAILED -> {
                layout_creating.visibility = View.GONE
                layout_create_failed.visibility = View.VISIBLE
                layout_create_success.visibility = View.GONE
            }
            Constants.STATE_CREATE_SUCCESS -> {
                layout_creating.visibility = View.GONE
                layout_create_failed.visibility = View.GONE
                layout_create_success.visibility = View.VISIBLE
            }
            else -> {
                layout_creating.visibility = View.VISIBLE
                layout_create_failed.visibility = View.GONE
                layout_create_success.visibility = View.GONE
            }
        }
    }
}