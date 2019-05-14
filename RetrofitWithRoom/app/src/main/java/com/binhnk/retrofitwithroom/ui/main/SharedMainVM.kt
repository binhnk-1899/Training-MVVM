package com.binhnk.retrofitwithroom.ui.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binhnk.retrofitwithroom.adapters.UserAdapter
import com.binhnk.retrofitwithroom.client.ClientController
import com.binhnk.retrofitwithroom.models.user.User
import com.binhnk.retrofitwithroom.models.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SharedMainVM : ViewModel() {

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
    val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        postValue(false)
    }

    fun setLoading(b: Boolean) {
        isLoading.postValue(b)
    }

    /**
     * start StorageActivity live data
     */
    val startStorageActivity = MutableLiveData<Boolean>()
        .apply {
            postValue(false)
        }

    fun setStartStorageActivity(b: Boolean) {
        startStorageActivity.postValue(b)
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
    fun loadUsers() {
        ClientController.requestGetListUser(currentPage, object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                isLoading.postValue(false)
                usersLiveData.postValue(ArrayList())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                isLoading.postValue(false)
                if (response.isSuccessful
                    && response.body() != null
                ) {
                    usersLiveData.postValue(response.body()!!.users)
                } else {
                    usersLiveData.postValue(ArrayList())
                }
            }

        })
    }


//    var mPostUserDialog: PostNewUserDialog? = null
//    var mPostUserSuccessDialog: PostNewUserSuccessDialog? = null
//    var job: String = ""
//    var name: String = ""
//
//    fun onJobTextChanged(c: CharSequence) {
//        job = c.toString()
//    }
//
//    fun onNameTextChanged(c: CharSequence) {
//        name = c.toString()
//    }
//
//    /**
//     * dissmiss post dialog
//     */
//    fun onCancelClicked() {
//        if (mPostUserDialog != null) {
//            try {
//                mPostUserDialog!!.dismiss()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    /**
//     * post user
//     */
//    fun onPostClicked(activity: Activity) {
//        onCancelClicked()
//        if (job != ""
//            && name != ""
//        ) {
//            ClientController.postUser(job, name, object : retrofit2.Callback<UserCreated> {
//                override fun onFailure(call: Call<UserCreated>, t: Throwable) {
//                    Utils.shortToast(activity.applicationContext, "Post failure")
//                }
//
//                override fun onResponse(call: Call<UserCreated>, response: Response<UserCreated>) {
//                    if (response.isSuccessful) {
//                        if (mPostUserSuccessDialog == null)
//                            mPostUserSuccessDialog =
//                                PostNewUserSuccessDialog(v.context, response.body()!!)
//                        mPostUserSuccessDialog!!.show()
//                        mPostUserSuccessDialog!!.window!!.setLayout(
//                            activity.applicationContext.resources.getDimensionPixelSize(R.dimen._250sdp),
//                            WindowManager.LayoutParams.WRAP_CONTENT
//                        )
//
//                    } else {
//                        Utils.shortToast(activity.applicationContext, "Post unsuccessful")
//                    }
//                }
//
//            })
//        }
//    }
//
//    /**
//     * add new user clicked
//     */
//    fun onAddNewUserClicked(activity: Activity) {
//        if (mPostUserDialog == null) {
//            mPostUserDialog = PostNewUserDialog(
//                activity,
//                this
//            )
//        }
//        mPostUserDialog!!.show()
//        mPostUserDialog!!.window!!.setLayout(
//            activity.applicationContext.resources.getDimensionPixelSize(R.dimen._250sdp),
//            WindowManager.LayoutParams.WRAP_CONTENT
//        )
//    }
//
//    /**
//     * start storage activity
//     */
//    fun startStorageActivity(activity: Activity) {
//        val animBundle = ActivityOptions.makeCustomAnimation(
//            activity,
//            R.anim.anim_enter_rtl,
//            R.anim.anim_exit_rtl
//        ).toBundle()
//        val storageIntent = Intent(activity, StorageActivity::class.java)
//        storageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        activity.startActivity(storageIntent, animBundle)
//    }
//
//    /**
//     * add new user to DB
//     */
//    fun addNewUserToDB(activity: Activity, mUser: User) {
//        Thread(Runnable {
//            val userDBRoom = UserDatabase.getInstance(activity.applicationContext)
//            val tempUser = userDBRoom.userDAO().getUserByUserId(mUser.id)
//            val userExist = tempUser != null
//            if (!userExist) {
//                userDBRoom.userDAO().insertUser(mUser)
//            } else {
//                activity.runOnUiThread {
//                    Utils.shortToast(
//                        activity.applicationContext,
//                        "User has been storage in database"
//                    )
//                }
//            }
//        }).start()
//    }
//
//    /**
//     * remove user from database
//     */
//    fun removeUserFromDB(activity: Activity, mUser: User) {
//        val mRemoveConfirmDialog =
//            RemoveConfirmDialog(activity, object : RemoveConfirmDialog.Callback {
//                override fun onOkClicked() {
//                    Thread(Runnable {
//                        val userDBRoom = UserDatabase.getInstance(activity.applicationContext)
//                        userDBRoom.userDAO().deleteUser(mUser)
//                    }).start()
//                }
//
//            })
//        mRemoveConfirmDialog.show()
//    }
}