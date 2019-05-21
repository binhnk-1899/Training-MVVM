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
    val userList = MutableLiveData<ArrayList<User>>().apply {
        Thread(Runnable {
            postValue(ArrayList())
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

    val userHasDeleted = SingleLiveEvent<Unit>()

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

    /**
     * remove user from db
     */
    fun removeUserFromDB() {
//        if (userClicked.value != null) {
//            Thread(Runnable {
//                if (userDAO.deleteUser(userClicked.value!!) > 0) {
//                    userHasDeleted.postCall()
//                    userList.postValue(ArrayList())
//                }
//            }).start()
//        }
    }

    /**
     * set menu item selected
     */
    fun setMenuItemSelected(value: Int) {
        menuItemSelected.postValue(value)
    }

}