package com.binhnk.clean.architecture.ui.main

import androidx.lifecycle.MutableLiveData
import com.binhnk.clean.architecture.base.BaseViewModel
import com.binhnk.clean.architecture.domain.usecase.user.GetUserUseCase
import com.binhnk.clean.architecture.model.UserItem
import com.binhnk.clean.architecture.model.UserItemMapper
import com.binhnk.clean.architecture.rx.SchedulerProvider

class MainViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val schedulerProvider: SchedulerProvider,
    private val userItemMapper: UserItemMapper
) : BaseViewModel(getUserUseCase) {

    val data = MutableLiveData<List<UserItem>>()
    val page = MutableLiveData<Int>().apply {
        postValue(1)
    }
    val loading = MutableLiveData<Boolean>().apply {
        postValue(false)
    }

    fun getUser() {
        page.value?.let { input ->
            if (input in 1..4) {
                loading.postValue(true)

                compositeDisposable.add(getUserUseCase.createObservable(GetUserUseCase.Params(page = input))
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doFinally { loading.postValue(false) }
                    .map { users ->
                        users.map { user ->
                            userItemMapper.mapToPresentation(user)
                        }
                    }
                    .subscribe({ users ->
                        data.postValue(users)
                    }, {})
                )

            }
        }
    }
}