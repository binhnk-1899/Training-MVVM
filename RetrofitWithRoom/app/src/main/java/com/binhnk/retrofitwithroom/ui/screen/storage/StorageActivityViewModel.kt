package com.binhnk.retrofitwithroom.ui.screen.storage

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.binhnk.retrofitwithroom.data.dao.UserDAO
import com.binhnk.retrofitwithroom.data.model.User
import com.binhnk.retrofitwithroom.ui.base.BaseViewModel
import com.binhnk.retrofitwithroom.utils.SingleLiveEvent

class StorageActivityViewModel(private val userDAO: UserDAO) : BaseViewModel() {

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

    val onCancelConfirmDialogPressed = SingleLiveEvent<Unit>()
    fun callOnCancelConfirmDialogPressed() {
        onCancelConfirmDialogPressed.call()
    }

    val onDeletePressed = SingleLiveEvent<Unit>()
    fun callOnDeletePressed() {
        onDeletePressed.call()
    }

    val onConfirmDeletePressed = SingleLiveEvent<Unit>()
    fun callOnConfirmDeletePressed() {
        onConfirmDeletePressed.call()
    }

    fun removeUserFromDB() {
        if (userClicked.value != null) {
            Thread(Runnable {
                userDAO.deleteUser(userClicked.value!!)
                userList.postValue(userDAO.getALlUser())
            }).start()
        }
    }

    val userClicked = MutableLiveData<User>().apply {
        postValue(null)
    }
}