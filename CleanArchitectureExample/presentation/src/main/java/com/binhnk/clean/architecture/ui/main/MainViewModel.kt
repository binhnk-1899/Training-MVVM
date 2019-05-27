package com.binhnk.clean.architecture.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.binhnk.clean.architecture.base.BaseViewModel
import com.binhnk.clean.architecture.domain.usecase.user.GetUserUseCase
import com.binhnk.clean.architecture.model.UserItem
import com.binhnk.clean.architecture.model.UserItemMapper
import com.binhnk.clean.architecture.rx.SchedulerProvider
import io.reactivex.Single
import io.reactivex.functions.Function4

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

    /**
     * get user per page
     */
    fun getUser() {
        page.value?.let { input ->
//            if (input in 1..4) {
                loading.postValue(true)

                compositeDisposable.add(
//                        getUserUseCase.createObservable(GetUserUseCase.Params(page = input))
//                        .subscribeOn(schedulerProvider.io())
//                        .observeOn(schedulerProvider.ui())
//                        .map { users ->
//                            users.map { user ->
//                                userItemMapper.mapToPresentation(user)
//                            }
//                        }
                        getUserSingle(input)
                                .doFinally { loading.postValue(false) }
                                .subscribe({ users ->
                                    data.postValue(users)
                                    Log.e("Ahihi", "${users.size}")
                                }, { Log.e("Ahihi", it.message!!) })
                )

//            }
        }
    }

    /**
     * get userItem single of page
     */
    private fun getUserSingle(page: Int): Single<List<UserItem>> {
        return getUserUseCase.createObservable(GetUserUseCase.Params(page = page))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .map { users ->
                    users.map { user ->
                        userItemMapper.mapToPresentation(user)
                    }
                }
    }

    /**
     * get users from all pages
     */
    @SuppressLint("CheckResult")
    fun getAllUser() {
        loading.postValue(true)

        val userObservable1 = getUserSingle(1)
        val userObservable2 = getUserSingle(2)
        val userObservable3 = getUserSingle(3)
        val userObservable4 = getUserSingle(4)

        // zip to collect observables
        Single.zip(userObservable1, userObservable2, userObservable3, userObservable4,
                Function4<List<UserItem>, List<UserItem>, List<UserItem>, List<UserItem>, List<UserItem>> { t1, t2, t3, t4 ->
                    collectAllList(t1, t2, t3, t4)
                })
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doFinally { loading.value = false }
                .subscribe(
                        { result ->
                            data.postValue(result)
                        },
                        { Throwable("Data not found") }
                )
    }

    /**
     * collect all list
     */
    private fun collectAllList(
            l1: List<UserItem>,
            l2: List<UserItem>,
            l3: List<UserItem>,
            l4: List<UserItem>
    ): ArrayList<UserItem> {
        val tmp = ArrayList<UserItem>()
        tmp.addAll(l1)
        tmp.addAll(l2)
        tmp.addAll(l3)
        tmp.addAll(l4)
        return tmp
    }

    fun changePage(mPage: Int) {
        if (mPage != page.value) {
            page.postValue(mPage)
        }
    }
}