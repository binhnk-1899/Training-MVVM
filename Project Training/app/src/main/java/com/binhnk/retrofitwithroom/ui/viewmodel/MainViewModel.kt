package com.binhnk.retrofitwithroom.ui.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.binhnk.retrofitwithroom.base.BaseViewModel
import com.binhnk.retrofitwithroom.data.dao.UserDAO
import com.binhnk.retrofitwithroom.data.model.User
import com.binhnk.retrofitwithroom.data.model.UserCreated
import com.binhnk.retrofitwithroom.data.repository.UserRepository
import com.binhnk.retrofitwithroom.data.scheduler.SchedulerProvider
import com.binhnk.retrofitwithroom.ui.adapter.UserAdapter
import com.binhnk.retrofitwithroom.utils.SingleLiveEvent
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function4


class MainViewModel(
    private val userDAO: UserDAO,
    private val userRepository: UserRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val currentPage = MutableLiveData<Int>().apply {
        postValue(1)
    }
    val isRefreshLoading = MutableLiveData<Boolean>().apply {
        postValue(false)
    }
    val goToStorage = SingleLiveEvent<Unit>()

    val postNewUserClicked = MutableLiveData<Boolean>().apply {
        postValue(false)
    }

    val userClientList = MutableLiveData<ArrayList<User>>().apply {
        postValue(ArrayList())
    }

    val userRoomList = MutableLiveData<ArrayList<User>>().apply {
        postValue(ArrayList())
    }

    val noDataClient = MutableLiveData<Boolean>().apply {
        postValue(true)
    }

    val noDataRoom = MutableLiveData<Boolean>().apply {
        postValue(true)
    }

    private val jobPost = MutableLiveData<String>().apply {
        postValue("")
    }

    val postClicked = SingleLiveEvent<Unit>()

    private var userPost = MutableLiveData<String>().apply {
        postValue("")
    }

    val cancelClicked = SingleLiveEvent<Unit>()

    val backClicked = SingleLiveEvent<Unit>()

    val postUserFailure = SingleLiveEvent<Unit>()

    val postUserSuccess = SingleLiveEvent<Unit>()

    val postUserUnSuccess = SingleLiveEvent<Unit>()

    val addUserToDBSuccess = SingleLiveEvent<Unit>()

    val addUserToDBFailure = SingleLiveEvent<Unit>()

    /**
     * user created
     */
    val userCreated = MutableLiveData<UserCreated>().apply { postValue(null) }

    /**
     * user clicked display information
     */
    val userClicked = MutableLiveData<User>().apply {
        postValue(null)
    }

    val deleteUser = SingleLiveEvent<Unit>()
    val confirmDeleteUser = SingleLiveEvent<Unit>()
    val userDeleted = SingleLiveEvent<Unit>()

    /**
     * user adapter
     */
    var userClientAdapter: UserAdapter? = null
    var userRoomAdapter: UserAdapter? = null

    /**
     * isLoading live data
     */
    fun callRefreshLoading(page: Int) {
        if (page != currentPage.value) {
            currentPage.postValue(page)
        }
    }

    fun callRefreshCurrentPage() {
        isRefreshLoading.postValue(true)
        loadUserApi()
    }

    /**
     * start StorageActivity live data
     */
    fun setStartStorageActivity() {
        goToStorage.call()
    }

    /**
     * post new user live data
     */
    fun setPostNewUserClicked(b: Boolean) {
        postNewUserClicked.postValue(b)
    }

    /**
     * set job of user
     */
    fun setJob(c: CharSequence) {
        jobPost.postValue(c.toString())
    }

    /**
     * name of user
     */
    fun setUser(c: CharSequence) {
        userPost.postValue(c.toString())
    }

    /**
     * action cancel
     */
    fun onCancelClicked() {
        cancelClicked.call()
    }

    /**
     * action back
     */
    fun onBackClicked() {
        backClicked.call()
    }

    /**
     * action post
     */
    fun postUser() {
        postClicked.call()
        compositeDisposable.add(
            userRepository.postUser(userPost.value!!, jobPost.value!!)
                .subscribeOn(schedulerProvider.io)
                .observeOn(schedulerProvider.ui)
                .subscribe(
                    { success ->
                        run {
                            userCreated.postValue(success)
                            postUserSuccess.call()
                        }
                    },
                    { postUserUnSuccess.call() }
                )
        )
    }

    /**
     * load user using RxJava and Retrofit
     */
    fun loadUserApi() {
        when {
            currentPage.value!! > 0 -> compositeDisposable.add(
                userRepository.getUsersUsingRx(currentPage.value)
                    .map { response ->
                        run {
                            for (user in response.users) {
                                user.addedInDB = userDAO.getUserByUserId(user.id) != null
                            }
                            response.users
                        }
                    }
                    .subscribeOn(schedulerProvider.io)
                    .observeOn(schedulerProvider.ui)
                    .subscribe(
                        { userResponses ->
                            run {
                                userClientList.value = userResponses
                                updateStateOfUsers()
                                isRefreshLoading.postValue(false)
                            }
                        },
                        {
                            run {
                                userClientList.value = ArrayList()
                                isRefreshLoading.postValue(false)
                            }
                        }
                    )
            )
            currentPage.value!! == 0 -> loadAllUsers()
            else -> {
                userClientList.postValue(ArrayList())
                isRefreshLoading.postValue(false)
            }
        }
    }

    /**
     * load all users from all pages
     */
    @SuppressLint("CheckResult")
    fun loadAllUsers() {
        val userObservable1 = getUserObservable(1)
        val userObservable2 = getUserObservable(2)
        val userObservable3 = getUserObservable(3)
        val userObservable4 = getUserObservable(4)

        // zip to collect observables
        Observable.zip(userObservable1, userObservable2, userObservable3, userObservable4,
            Function4<ArrayList<User>, ArrayList<User>, ArrayList<User>, ArrayList<User>, ArrayList<User>> { t1, t2, t3, t4 ->
                collectAllList(t1, t2, t3, t4)
            }).subscribeOn(schedulerProvider.newThread)
            .observeOn(schedulerProvider.ui)
            .subscribe {
                userClientList.postValue(it)
                isRefreshLoading.postValue(false)
            }
    }

    /**
     * get user observable by page
     */
    private fun getUserObservable(page: Int): Observable<ArrayList<User>> {
        return userRepository.getUsersUsingRx(page)
            .map { response ->
                run {
                    for (user in response.users) {
                        user.addedInDB = userDAO.getUserByUserId(user.id) != null
                    }
                    response.users
                }
            }
            .subscribeOn(schedulerProvider.newThread)
            .observeOn(schedulerProvider.ui)
    }

    /**
     * collect all list
     */
    private fun collectAllList(
        l1: ArrayList<User>,
        l2: ArrayList<User>,
        l3: ArrayList<User>,
        l4: ArrayList<User>
    ): ArrayList<User> {
        val tmp = ArrayList<User>()
        tmp.addAll(l1)
        tmp.addAll(l2)
        tmp.addAll(l3)
        tmp.addAll(l4)
        return tmp
    }

    /**
     * insert user to Room database
     */
    fun insertUserToRoomDB(user: User) {
        Completable.fromAction {
            userDAO.insertUserByRx(user)
        }.observeOn(schedulerProvider.ui)
            .subscribeOn(schedulerProvider.io)
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() {
                    addUserToDBSuccess.call()
                    updateStateOfUsers()
                }

                override fun onError(e: Throwable) {
                    addUserToDBFailure.call()
                }
            })
    }

    /**
     * query all user in Room using RxJava
     */
    fun queryAllUserUsingRx() {
        compositeDisposable.add(
            userDAO.getALlUser().subscribeOn(schedulerProvider.io)
                .observeOn(schedulerProvider.ui)
                .subscribe(
                    { response ->
                        run {
                            Log.e("Ahihi", "${response.size}")
                            userRoomList.postValue(ArrayList(response))
                        }
                    },
                    {
                        run {
                            it.printStackTrace()
                            userRoomList.postValue(ArrayList())
                        }
                    }
                ))
    }

    /**
     * update state of all user
     */
    @SuppressLint("CheckResult")
    fun updateStateOfUsers() {
        if (userClientList.value.isNullOrEmpty()) {
            Log.e("Ahihi", "Update empty")
            return
        } else {
            Log.e("Ahihi", "Update not empty")
            for (i in 0 until userClientList.value!!.size) {
                userClientList.value!![i].addedInDB =
                    userDAO.getUserByUserId(userClientList.value!![i].id) != null
                userClientAdapter?.notifyItemChanged(i, "update_check")
            }
        }
    }

    /**
     * delete user from Room
     */
    fun deleteUser() {
        if (userClicked.value != null) {
            Completable.fromAction {
                userDAO.deleteUser(userClicked.value!!)
            }
                .subscribeOn(schedulerProvider.io)
                .observeOn(schedulerProvider.ui)
                .subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        userRoomList.value!!.remove(userClicked.value)
                        userClicked.postValue(null)
                        userDeleted.call()
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        userClicked.postValue(null)
                    }

                })
        }
    }
}