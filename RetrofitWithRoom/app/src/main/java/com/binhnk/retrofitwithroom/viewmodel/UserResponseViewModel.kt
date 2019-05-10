package com.binhnk.retrofitwithroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.binhnk.retrofitwithroom.models.user.UsersResponse

class UserResponseViewModel(application: Application) : AndroidViewModel(application) {
    var userResponseLiveData : MutableLiveData<UsersResponse> = MutableLiveData()
}