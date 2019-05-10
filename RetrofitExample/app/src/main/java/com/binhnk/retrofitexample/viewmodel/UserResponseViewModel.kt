package com.binhnk.retrofitexample.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.binhnk.retrofitexample.models.user.UsersResponse

class UserResponseViewModel(application: Application) : AndroidViewModel(application) {
    var userResponseLiveData : MutableLiveData<UsersResponse> = MutableLiveData()
}