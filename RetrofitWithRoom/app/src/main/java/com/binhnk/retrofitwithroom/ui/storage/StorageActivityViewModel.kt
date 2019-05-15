package com.binhnk.retrofitwithroom.ui.storage

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.binhnk.retrofitwithroom.data.dao.UserDAO
import com.binhnk.retrofitwithroom.models.user.User
import com.binhnk.retrofitwithroom.ui.base.BaseViewModel
import com.binhnk.retrofitwithroom.utils.SingleLiveEvent

class StorageActivityViewModel (private val userDAO: UserDAO): BaseViewModel() {

    val noDataVisibility = MutableLiveData<Int>().apply {
        postValue(View.VISIBLE)
    }
    val userList = MutableLiveData<List<User>>().apply {
        Thread(Runnable {
            this.postValue(userDAO.getALlUser())
        }).start()
    }

    val onBackPressed = SingleLiveEvent<Unit>()
    fun callOnBackPressed() {
        onBackPressed.call()
    }

    val onCancelPressed = SingleLiveEvent<Unit>()
    fun callOnCancelPressed() {
        onCancelPressed.call()
    }

    val userClicked = MutableLiveData<User>().apply {
        postValue(null)
    }
    fun getUserID(): String {
        return if (userClicked.value != null) {
            userClicked.value!!.id.toString()
        } else {
            ""
        }
    }
}