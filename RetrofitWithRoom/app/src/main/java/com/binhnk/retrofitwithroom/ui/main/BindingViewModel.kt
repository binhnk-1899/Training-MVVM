package com.binhnk.retrofitwithroom.ui.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binhnk.retrofitwithroom.client.ClientController
import com.binhnk.retrofitwithroom.models.user.User
import com.binhnk.retrofitwithroom.models.user.UserResponse
import com.binhnk.retrofitwithroom.ui.dialogs.PostNewUserDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BindingViewModel : ViewModel() {

    val currentPage: MutableLiveData<String> = MutableLiveData()

    var userLiveData: MutableLiveData<ArrayList<User>> = MutableLiveData()

    fun setValueForCurrentPage(s: CharSequence) {
        currentPage.postValue(s.toString())
    }

    /**
     * on getData button clicked
     */
    fun onGetDataClicked() {
        val page = currentPage.value!!.toInt()
        ClientController().requestGetListUser(page, object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                userLiveData.postValue(ArrayList())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    userLiveData.postValue(response.body()!!.users)
                } else {
                    userLiveData.postValue(ArrayList())
                }
            }

        })
    }

    /**
     * add new user clicked
     */
    fun onAddNewUserClicked(v: View) {
        val mPostUserDialog = PostNewUserDialog(v.context, object : PostNewUserDialog.Callback {
            override fun onSubmit(
                name: String,
                job: String
            ) {
//                addNewUser(name, job)
            }

        })
        mPostUserDialog.show()
    }
}