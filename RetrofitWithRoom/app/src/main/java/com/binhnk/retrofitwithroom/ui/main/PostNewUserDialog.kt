package com.binhnk.retrofitwithroom.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.client.ClientController
import com.binhnk.retrofitwithroom.models.user.UserCreated
import com.binhnk.retrofitwithroom.ui.base.BaseDialogFragment
import com.binhnk.retrofitwithroom.utils.Utils
import org.koin.androidx.viewmodel.ext.sharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostNewUserDialog :
    BaseDialogFragment<com.binhnk.retrofitwithroom.databinding.DialogPostNewUserBinding>() {

    override val layoutId: Int
        get() = R.layout.dialog_post_new_user

    private val mViewModel by sharedViewModel<MainActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.viewModel = mViewModel

        mViewModel.apply {
            cancelClicked.observe(this@PostNewUserDialog, Observer {
                dismiss()
            })
            postClicked.observe(this@PostNewUserDialog, Observer {
                dismiss()
                ClientController.postUser(userPost.value!!, jobPost.value!!, object : Callback<UserCreated> {
                    override fun onFailure(call: Call<UserCreated>, t: Throwable) {
                        Utils.shortToast(context!!, "Post user failure")
                    }

                    override fun onResponse(
                        call: Call<UserCreated>,
                        response: Response<UserCreated>
                    ) {
                        if (response.isSuccessful
                            && response.body() != null) {
                            userCreated.postValue(response.body())
                        } else {
                            Utils.shortToast(context!!, "Post user unsuccessful")
                        }
                    }

                })
            })
        }
    }
}