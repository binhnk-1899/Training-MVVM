package com.binhnk.retrofitwithroom.ui.screen.main

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.binhnk.retrofitwithroom.data.dao.UserDAO
import com.binhnk.retrofitwithroom.data.model.User
import com.binhnk.retrofitwithroom.data.model.UserCreated
import com.binhnk.retrofitwithroom.data.remote.response.UserResponse
import com.binhnk.retrofitwithroom.data.repository.UserRepository
import com.binhnk.retrofitwithroom.ui.adapters.UserAdapter
import com.binhnk.retrofitwithroom.ui.base.BaseViewModel
import com.binhnk.retrofitwithroom.utils.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivityViewModel(
    private val userDAO: UserDAO,
    private val userRepository: UserRepository
) : BaseViewModel() {

    var currentPage: Int = -1
    val isRefreshLoading = ObservableField<Boolean>().apply {
        set(false)
    }
    val startStorageActivity = SingleLiveEvent<Unit>()

    val postNewUserClicked = MutableLiveData<Boolean>()
        .apply {
            postValue(false)
        }

    val usersLiveData: MutableLiveData<ArrayList<User>> = MutableLiveData()

    val noDataVisible = MutableLiveData<Boolean>()
        .apply {
            postValue(
                usersLiveData.value.isNullOrEmpty()
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

    val postUserFailure = SingleLiveEvent<Unit>()

    val postUserSuccess = SingleLiveEvent<Unit>()

    val postUserUnSuccess = SingleLiveEvent<Unit>()

    /**
     * user created
     */
    val userCreated = MutableLiveData<UserCreated>().apply { postValue(null) }

    /**
     * user adapter
     */
    var userAdapter: UserAdapter? = null

    /**
     * currentPage live data
     */
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
    fun callRefreshLoading() {
        isRefreshLoading.set(true)
        loadUsers()
    }

    /**
     * start StorageActivity live data
     */
    fun setStartStorageActivity() {
        startStorageActivity.call()
    }

    /**
     * post new user live data
     */
    fun setPostNewUserClicked(b: Boolean) {
        postNewUserClicked.postValue(b)
    }

    /**
     * load user using retrofit
     */
    private fun loadUsers() {
        userRepository.getUsers(currentPage).enqueue(object : Callback<UserResponse> {
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
     * add user to database
     */
    fun addUserToDB(user: User): Long {
        return userDAO.insertUser(user)
    }
}