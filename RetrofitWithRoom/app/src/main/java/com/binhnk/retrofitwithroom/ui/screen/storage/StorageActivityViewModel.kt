package com.binhnk.retrofitwithroom.ui.screen.storage

import androidx.lifecycle.MutableLiveData
import com.binhnk.retrofitwithroom.data.constants.Constants.MENU_SORT_NAME_AZ
import com.binhnk.retrofitwithroom.data.dao.UserDAO
import com.binhnk.retrofitwithroom.data.model.User
import com.binhnk.retrofitwithroom.ui.base.BaseViewModel
import com.binhnk.retrofitwithroom.utils.SingleLiveEvent

class StorageActivityViewModel(val userDAO: UserDAO) : BaseViewModel() {

    val noDataVisibility = MutableLiveData<Boolean>().apply {
        postValue(true)
    }
    val userList = MutableLiveData<List<User>>().apply {
        Thread(Runnable {
            this.postValue(userDAO.getALlUser())
        }).start()
    }

    val onBackPressed = SingleLiveEvent<Unit>()
    val onMenuPressed = SingleLiveEvent<Unit>()
    val onCancelPressed = SingleLiveEvent<Unit>()
    val onCancelConfirmDialogPressed = SingleLiveEvent<Unit>()
    val onDeletePressed = SingleLiveEvent<Unit>()
    val onConfirmDeletePressed = SingleLiveEvent<Unit>()
    val userClicked = MutableLiveData<User>().apply {
        postValue(null)
    }
    val menuItemSelected = MutableLiveData<Int>().apply {
        postValue(MENU_SORT_NAME_AZ)
    }

    fun callOnBackPressed() {
        onBackPressed.call()
    }

    fun callOnMenuPressed() {
        onMenuPressed.call()
    }

    fun callOnCancelPressed() {
        onCancelPressed.call()
    }

    fun callOnCancelConfirmDialogPressed() {
        onCancelConfirmDialogPressed.call()
    }

    fun callOnDeletePressed() {
        onDeletePressed.call()
    }

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

    fun setMenuItemSelected(value: Int) {
        menuItemSelected.postValue(value)
    }

}