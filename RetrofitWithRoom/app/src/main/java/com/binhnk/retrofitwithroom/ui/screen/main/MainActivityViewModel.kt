package com.binhnk.retrofitwithroom.ui.screen.main

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.binhnk.retrofitwithroom.data.dao.UserDAO
import com.binhnk.retrofitwithroom.data.model.User
import com.binhnk.retrofitwithroom.data.model.UserCreated
import com.binhnk.retrofitwithroom.data.remote.APICallback
import com.binhnk.retrofitwithroom.data.remote.ApiService
import com.binhnk.retrofitwithroom.data.remote.response.UserResponse
import com.binhnk.retrofitwithroom.ui.adapters.UserAdapter
import com.binhnk.retrofitwithroom.ui.base.BaseViewModel
import com.binhnk.retrofitwithroom.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivityViewModel(
    private val userDAO: UserDAO
//    private val apiCallback: APICallback
) : BaseViewModel() {

    /**
     * currentPage live data
     */
    var currentPage: Int = 0

    fun setValueForCurrentPage(s: CharSequence) {
        currentPage = if (s.toString() != "") {
            s.toString().toInt()
        } else {
            0
        }
    }

    /**
     * isLoading live data
     */
    val isRefreshLoading = ObservableField<Boolean>().apply {
        set(false)
    }

    fun callRefreshLoading() {
        isRefreshLoading.set(true)
        loadUsers()
    }

    /**
     * start StorageActivity live data
     */
    val startStorageActivity = SingleLiveEvent<Unit>()

    fun setStartStorageActivity() {
        startStorageActivity.call()
    }

    /**
     * post new user live data
     */
    val postNewUserClicked = MutableLiveData<Boolean>()
        .apply {
            postValue(false)
        }

    fun setPostNewUserClicked(b: Boolean) {
        postNewUserClicked.postValue(b)
    }

    /**
     * userList live data
     */
    var usersLiveData: MutableLiveData<ArrayList<User>> = MutableLiveData()

    /**
     * tvNoData visible live data
     */
    var noDataVisible = MutableLiveData<Int>()
        .apply {
            postValue(
                if (usersLiveData.value.isNullOrEmpty()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            )
        }

    /**
     * user adapter
     */
    var userAdapter: UserAdapter? = null

    /**
     * load user using retrofit
     */
    private fun loadUsers() {
//        apiCallback.getAllUsers(currentPage.toString()).enqueue(object : Callback<UserResponse> {
//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                usersLiveData.postValue(ArrayList())
//                isRefreshLoading.set(false)
//            }
//
//            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                if (response.isSuccessful
//                    && response.body() != null
//                ) {
//                    usersLiveData.postValue(response.body()!!.users)
//                } else {
//                    usersLiveData.postValue(ArrayList())
//                }
//                isRefreshLoading.set(false)
//
//            }
//
//        })
        ApiService.requestGetListUser(currentPage, object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                usersLiveData.postValue(ArrayList())
                isRefreshLoading.set(false)
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful
                    && response.body() != null
                ) {
                    usersLiveData.postValue(response.body()!!.users)
                } else {
                    usersLiveData.postValue(ArrayList())
                }
                isRefreshLoading.set(false)

            }

        })
    }

    /**
     * job of user
     */
    var jobPost = MutableLiveData<String>().apply {
        postValue("")
    }

    fun setJob(c: CharSequence) {
        jobPost.postValue(c.toString())
    }

    /**
     * name of user
     */
    var userPost = MutableLiveData<String>().apply {
        postValue("")
    }

    fun setUser(c: CharSequence) {
        userPost.postValue(c.toString())
    }

    /**
     * action cancel
     */
    val cancelClicked = SingleLiveEvent<Unit>()

    fun onCancelClicked() {
        cancelClicked.call()
    }

    /**
     * action post
     */
    val postClicked = SingleLiveEvent<Unit>()

    fun onPostClicked() {
        postClicked.call()
    }

    /**
     * user created
     */
    val userCreated = MutableLiveData<UserCreated>().apply { postValue(null) }

    /**
     * add user to database
     */
    fun addUserToDB(user: User): Long {
        return userDAO.insertUser(user)
    }
}