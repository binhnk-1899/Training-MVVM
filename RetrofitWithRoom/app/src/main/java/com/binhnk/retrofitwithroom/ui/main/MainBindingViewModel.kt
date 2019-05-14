package com.binhnk.retrofitwithroom.ui.main

import android.app.Activity
import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.adapters.UserAdapter
import com.binhnk.retrofitwithroom.client.ClientController
import com.binhnk.retrofitwithroom.db.UserDatabase
import com.binhnk.retrofitwithroom.models.user.User
import com.binhnk.retrofitwithroom.models.user.UserCreated
import com.binhnk.retrofitwithroom.models.user.UserResponse
import com.binhnk.retrofitwithroom.ui.dialogs.RemoveConfirmDialog
import com.binhnk.retrofitwithroom.ui.dialogs.postuser.PostNewUserDialog
import com.binhnk.retrofitwithroom.ui.dialogs.postuser.PostNewUserSuccessDialog
import com.binhnk.retrofitwithroom.ui.storage.StorageActivity
import com.binhnk.retrofitwithroom.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainBindingViewModel : ViewModel() {

    var userAdapter: UserAdapter? = null

    private val currentPage: MutableLiveData<String> = MutableLiveData()

    val isGettingData: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        postValue(false) // khởi tạo giá trị ban đầu
    }

    var userLiveData: MutableLiveData<ArrayList<User>> = MutableLiveData()

    fun setValueForCurrentPage(s: CharSequence) {
        currentPage.postValue(s.toString())
    }

    /**
     * refresh data when swipe
     */
    fun onRefresh(activity: Activity) {
        isGettingData.postValue(true)
        bindData(activity)
    }

    /**
     * on getData button clicked
     */
    fun bindData(activity: Activity) {
        if (currentPage.value != null) {
            isGettingData.postValue(true)
            val page = currentPage.value!!.toInt()
            ClientController.requestGetListUser(page, object : Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    userLiveData.postValue(ArrayList())
                    Log.e("ahihi", "Failure")
                    isGettingData.postValue(false)
                }

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        userLiveData.postValue(response.body()!!.users)
                        Log.e("ahihi", "Successful")
                    } else {
                        userLiveData.postValue(ArrayList())
                        Log.e("ahihi", "Unsuccessful")
                    }
                    isGettingData.postValue(false)
                }

            })
        } else {
            Utils.shortToast(activity.applicationContext, "Please enter page entry")
            isGettingData.postValue(false)
        }
    }

    var mPostUserDialog: PostNewUserDialog? = null
    var job: String = ""
    var name: String = ""

    fun onJobTextChanged(c: CharSequence) {
        job = c.toString()
    }

    fun onNameTextChanged(c: CharSequence) {
        name = c.toString()
    }

    /**
     * dissmiss post dialog
     */
    fun onCancelClicked() {
        if (mPostUserDialog != null) {
            try {
                mPostUserDialog!!.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * post user
     */
    fun onPostClicked(v: View) {
        onCancelClicked()
        if (job != ""
            && name != "") {
            ClientController.postUser(job, name, object : retrofit2.Callback<UserCreated> {
                override fun onFailure(call: Call<UserCreated>, t: Throwable) {
                     Utils.shortToast(v.context, "Post failure")
                }

                override fun onResponse(call: Call<UserCreated>, response: Response<UserCreated>) {
                    if (response.isSuccessful) {
                         val mSuccessDialog = PostNewUserSuccessDialog(v.context, response.body()!!)
                        mSuccessDialog.show()
                    } else {
                        Utils.shortToast(v.context, "Post unsuccessful")
                    }
                }

            })
        }
    }

    /**
     * add new user clicked
     */
    fun onAddNewUserClicked(activity: Activity) {
        if (mPostUserDialog == null) {
            mPostUserDialog = PostNewUserDialog(
                activity,
                this)
        }
        mPostUserDialog!!.show()
        mPostUserDialog!!.window!!.setLayout(
            activity.applicationContext.resources.getDimensionPixelSize(R.dimen._250sdp),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    /**
     * start storage activity
     */
    fun startStorageActivity(activity: Activity) {
        val animBundle = ActivityOptions.makeCustomAnimation(
            activity,
            R.anim.anim_enter_rtl,
            R.anim.anim_exit_rtl
        ).toBundle()
        val storageIntent = Intent(activity, StorageActivity::class.java)
        storageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(storageIntent, animBundle)
    }

    /**
     * add new user to DB
     */
    fun addNewUserToDB(activity: Activity, mUser: User) {
        Thread(Runnable {
            val userDBRoom = UserDatabase.getInstance(activity.applicationContext)
            val tempUser = userDBRoom.userDAO().getUserByUserId(mUser.id)
            val userExist = tempUser != null
            if (!userExist) {
                userDBRoom.userDAO().insertUser(mUser)
            } else {
                activity.runOnUiThread {
                    Utils.shortToast(
                        activity.applicationContext,
                        "User has been storage in database"
                    )
                }
            }
        }).start()
    }

    /**
     * remove user from database
     */
    fun removeUserFromDB(activity: Activity, mUser: User) {
        val mRemoveConfirmDialog =
            RemoveConfirmDialog(activity, object : RemoveConfirmDialog.Callback {
                override fun onOkClicked() {
                    Thread(Runnable {
                        val userDBRoom = UserDatabase.getInstance(activity.applicationContext)
                        userDBRoom.userDAO().deleteUser(mUser)
                    }).start()
                }

            })
        mRemoveConfirmDialog.show()
    }
}