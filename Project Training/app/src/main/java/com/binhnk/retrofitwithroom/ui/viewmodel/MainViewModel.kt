package com.binhnk.retrofitwithroom.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.binhnk.retrofitwithroom.data.dao.UserDAO
import com.binhnk.retrofitwithroom.data.model.User
import com.binhnk.retrofitwithroom.data.model.UserCreated
import com.binhnk.retrofitwithroom.data.repository.UserRepository
import com.binhnk.retrofitwithroom.ui.adapter.UserAdapter
import com.binhnk.retrofitwithroom.ui.base.BaseViewModel
import com.binhnk.retrofitwithroom.utils.SingleLiveEvent
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(
    val userDAO: UserDAO,
    private val userRepository: UserRepository
) : BaseViewModel() {

    var currentPage: Int = -1
    val isRefreshLoading = MutableLiveData<Boolean>().apply {
        postValue(false)
    }
    val isLoadingRepository = MutableLiveData<Boolean>().apply {
        postValue(false)
    }
    val goToStorage = SingleLiveEvent<Unit>()

    val postNewUserClicked = MutableLiveData<Boolean>()
        .apply {
            postValue(false)
        }

    val userClientList = MutableLiveData<ArrayList<User>>().apply {
        postValue(ArrayList())
    }

    val userRepositoryList = MutableLiveData<ArrayList<User>>().apply {
        postValue(ArrayList())
    }

    val noDataVisible = MutableLiveData<Boolean>()
        .apply {
            postValue(
                userClientList.value.isNullOrEmpty()
            )
        }

    val noDataRepository = MutableLiveData<Boolean>()
        .apply {
            postValue(
                userRepositoryList.value.isNullOrEmpty()
            )
        }

    private val jobPost = MutableLiveData<String>().apply {
        postValue("")
    }

    val postClicked = SingleLiveEvent<Unit>()

    var userPost = MutableLiveData<String>().apply {
        postValue("")
    }

    val cancelClicked = SingleLiveEvent<Unit>()

    val backClicked = SingleLiveEvent<Unit>()

    val postUserFailure = SingleLiveEvent<Unit>()

    val postUserSuccess = SingleLiveEvent<Unit>()

    val postUserUnSuccess = SingleLiveEvent<Unit>()

    val addUserToDBSuccess = MutableLiveData<Int>().apply {
        postValue(-1)
    }
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

    /**
     * user adapter
     */
    var userDaoAdapter: UserAdapter? = null
    var userRoomAdapter: UserAdapter? = null

    /**
     * currentPage live data
     */
    fun setValueForCurrentPage(s: CharSequence) {
        currentPage = if (s.toString() != "") {
            s.toString().toInt()
        } else {
            -1
        }
    }

    /**
     * isLoading live data
     */
    fun callRefreshLoading() {
        isRefreshLoading.postValue(true)
        loadUserUsingRx()
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
     * load user using RxJava and Retrofit
     */
    private fun loadUserUsingRx() {
        if (currentPage >= 0) {
            compositeDisposable.add(
                userRepository.getUsersUsingRx(currentPage)
                    .subscribeOn(Schedulers.io())
                    .map { userResponses -> userResponses }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { userResponses ->
                            run {
                                userClientList.postValue(userResponses.users)
                                isRefreshLoading.postValue(false)
                            }
                        },
                        {
                            run {
                                userClientList.postValue(ArrayList())
                                isRefreshLoading.postValue(false)
                            }
                        }
                    )
            )
        } else {
            userClientList.postValue(ArrayList())
            isRefreshLoading.postValue(false)
        }
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
     * delete user
     */
    fun onDeleteUser() {
        deleteUser.call()
    }

    /**
     * action post
     */
    fun onPostClicked() {
        postClicked.call()
        userRepository.postUser(userPost.value!!, jobPost.value!!)
            .enqueue(object : Callback<UserCreated> {
                override fun onFailure(call: Call<UserCreated>, t: Throwable) {
                    postUserFailure.call()
                }

                override fun onResponse(
                    call: Call<UserCreated>,
                    response: Response<UserCreated>
                ) {
                    if (response.isSuccessful
                        && response.body() != null
                    ) {
                        userCreated.postValue(response.body())
                        postUserSuccess.call()
                    } else {
                        postUserUnSuccess.call()
                    }
                }

            })
    }

    /**
     * add user to Room database
     */
    fun addUser(user: User) {
        Completable.fromAction {
            userDAO.insertUserByRx(user)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() {
                    addUserToDBSuccess.postValue(user.id)
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
        isLoadingRepository.postValue(true)
        compositeDisposable.add(userDAO.getALlUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    run {
                        Log.e("Ahihi", "${response.size}")
                        userRepositoryList.postValue(ArrayList(response))
                        isLoadingRepository.postValue(false)
                    }
                },
                {
                    run {
                        it.printStackTrace()
                        Log.e("Ahihi", "Error: 0")
                        userRepositoryList.postValue(ArrayList())
                        isLoadingRepository.postValue(false)
                    }
                }
            ))
    }

    fun deleteUser() {
        if (userClicked.value != null) {
            Completable.fromAction {
                userDAO.deleteUser(userClicked.value!!)
            }
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        userRepositoryList.value!!.remove(userClicked.value)
                        userClicked.postValue(null)
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