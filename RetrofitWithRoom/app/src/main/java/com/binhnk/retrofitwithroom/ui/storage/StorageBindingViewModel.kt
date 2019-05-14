package com.binhnk.retrofitwithroom.ui.storage

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binhnk.retrofitwithroom.adapters.UserAdapter
import com.binhnk.retrofitwithroom.models.user.User

class StorageBindingViewModel : ViewModel() {

    val tvNoDataVisibility = MutableLiveData<Int>().apply {
        postValue(View.VISIBLE)
    }
    val userListLiveData = MutableLiveData<List<User>>().apply {
        emptyList<User>()
    }

    var userAdapter: UserAdapter? = null
}