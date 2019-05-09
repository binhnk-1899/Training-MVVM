package com.binhnk.demogooglenews.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.binhnk.demogooglenews.models.ClassInformation

class ClassInformationViewModel(application: Application) : AndroidViewModel(application) {
    var classLiveData: MutableLiveData<ArrayList<ClassInformation>> = MutableLiveData()
}