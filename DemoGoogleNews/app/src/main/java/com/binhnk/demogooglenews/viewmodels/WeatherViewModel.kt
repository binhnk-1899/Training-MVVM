package com.binhnk.demogooglenews.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.binhnk.demogooglenews.models.Article


class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    var articleLiveData: MutableLiveData<ArrayList<Article>> = MutableLiveData()
}