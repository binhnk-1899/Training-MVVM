package com.binhnk.retrofitwithroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.binhnk.retrofitwithroom.models.user.User

class UserViewModel(application: Application) : AndroidViewModel(application) {
    var userLiveData : MutableLiveData<ArrayList<User>> = MutableLiveData()
}