package com.binhnk.retrofitwithroom.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binhnk.retrofitwithroom.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel  : ViewModel() {

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
//        useCases.let { userCases ->
//            if (userCases.isNotEmpty()) userCases.forEach { it?.onCleared() }
//        }
        super.onCleared()
    }
}
