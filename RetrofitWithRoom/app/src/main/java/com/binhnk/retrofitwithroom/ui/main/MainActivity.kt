package com.binhnk.retrofitwithroom.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.adapters.UserAdapter
import com.binhnk.retrofitwithroom.client.ClientController
import com.binhnk.retrofitwithroom.databinding.ActivityMainBinding
import com.binhnk.retrofitwithroom.models.user.User
import com.binhnk.retrofitwithroom.models.user.UserCreated
import com.binhnk.retrofitwithroom.models.user.UserResponse
import com.binhnk.retrofitwithroom.room.UserDatabase
import com.binhnk.retrofitwithroom.ui.dialogs.AddNewUserSuccessDialog
import com.binhnk.retrofitwithroom.ui.dialogs.PostNewUserDialog
import com.binhnk.retrofitwithroom.ui.dialogs.RemoveConfirmDialog
import com.binhnk.retrofitwithroom.viewmodel.UserResponseViewModel
import com.binhnk.retrofitwithroom.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mContext: Context

    // UI
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var rvUser: RecyclerView
    private lateinit var mAdapter: UserAdapter

    private lateinit var tvUserRoomCount: TextView
    private lateinit var btnGetData: Button
    private lateinit var btnPostUser: Button

    private var mUserResponseVM: UserResponseViewModel? = null
    private var mUserVM: UserViewModel? = null
    private var userDBRoom: UserDatabase? = null
    private var pageValue = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this@MainActivity

        val bindingViewModel = ViewModelProviders.of(this).get(BindingViewModel::class.java)

        DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        ).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = bindingViewModel
        }

//        declareUserLiveData()
//        declareUserResponseLiveData()
//        declareDAOCountLiveData(mContext)

        initUI()
        declareAdapter()

        bindingViewModel.userLiveData.observe(this, Observer {
            mAdapter.updateAdapter(it!!)
        })
//        declareAdapter()
//        initAction()
//
//        startLoadUserDataFromAPI()
    }

    /**
     * init UI
     */
    private fun initUI() {
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        rvUser = findViewById(R.id.rv_user)
        tvUserRoomCount = findViewById(R.id.tv_datum_dao_count)
        btnGetData = findViewById(R.id.btn_get_data)
        btnPostUser = findViewById(R.id.btn_post_user)
    }

    /**
     * declare adapter
     */
    private fun declareAdapter() {
        mAdapter = UserAdapter(mContext, object : UserAdapter.Callback {
            override fun onItemLongClicked(mUserClicked: User) {
                val mRemoveConfirmDialog =
                    RemoveConfirmDialog(mContext, object : RemoveConfirmDialog.Callback {
                        override fun onOkClicked() {
                            Thread(Runnable {
                                if (userDBRoom == null) {
                                    userDBRoom = UserDatabase.getInstance(mContext)
                                }
                                userDBRoom!!.userDAO().deleteUser(mUserClicked)
                            }).start()
                        }

                    })
                mRemoveConfirmDialog.show()
            }

            override fun onItemClicked(mUserClicked: User) {
                Thread(Runnable {
                    if (userDBRoom == null) {
                        userDBRoom = UserDatabase.getInstance(mContext)
                    }
                    val tempUser = userDBRoom!!.userDAO().getUserByUserId(mUserClicked.id)
                    val userExist = tempUser != null
                    if (!userExist) {
                        userDBRoom!!.userDAO().insertUser(mUserClicked)
                    } else {
                        runOnUiThread {
                            Toast.makeText(
                                mContext,
                                "User has been storage in database",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }).start()

            }
        })
        rvUser.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rvUser.adapter = mAdapter
    }

    /**
     * init action
     */
    private fun initAction() {
        swipeRefreshLayout.setOnRefreshListener {
            startLoadUserDataFromAPI()
        }
        btnGetData.setOnClickListener {
            if (edt_page_number.text.toString() != "") {
                pageValue = edt_page_number.text.toString().toInt()
                startLoadUserDataFromAPI()
            } else {
                Toast.makeText(mContext, "Please enter page number", Toast.LENGTH_SHORT).show()
            }
        }
        btnPostUser.setOnClickListener {
            val mPostUserDialog = PostNewUserDialog(mContext, object : PostNewUserDialog.Callback {
                override fun onSubmit(
                    name: String,
                    job: String
                ) {
                    addNewUser(name, job)
                }

            })
            mPostUserDialog.show()
        }
    }

    /**
     * add new user
     */
    private fun addNewUser(
        name: String,
        job: String
    ) {
        ClientController().postUser(name, job, object : retrofit2.Callback<UserCreated> {
            override fun onFailure(call: Call<UserCreated>, t: Throwable) {
                Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<UserCreated>, response: Response<UserCreated>) {
                if (response.isSuccessful) {
                    val mSuccessDialog = AddNewUserSuccessDialog(mContext, response.body()!!)
                    mSuccessDialog.show()
                } else {
                    Toast.makeText(mContext, "Response not success", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    /**
     * start load user userLiveData
     */
    private fun startLoadUserDataFromAPI() {
        Handler().post {
            if (!swipeRefreshLayout.isRefreshing) {
                swipeRefreshLayout.isRefreshing = true
            }
        }
        ClientController().requestGetListUser(
            pageValue,
            object : retrofit2.Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(mContext, "Request failure", Toast.LENGTH_SHORT).show()
                    mUserResponseVM!!.userResponseLiveData.postValue(null)
                    swipeRefreshLayout.isRefreshing = false
                }

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            mContext,
                            "Request get response successful",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        mUserResponseVM!!.userResponseLiveData.postValue(response.body()!!)
                    } else {
                        Toast.makeText(
                            mContext,
                            "Request get response unsuccessful",
                            Toast.LENGTH_SHORT
                        ).show()
                        mUserResponseVM!!.userResponseLiveData.postValue(null)
                    }
                    swipeRefreshLayout.isRefreshing = false
                }

            })
    }

    /**
     * load user data from Room when API request failure
     */
    private fun loadUserDataFromRoom(page: Int) {
        if (userDBRoom == null) {
            userDBRoom = UserDatabase.getInstance(mContext)
        }
        mUserVM!!.userLiveData.postValue(ArrayList(userDBRoom!!.userDAO().getUsersByPage(page)))
    }

    /**
     * declare datum DAO count live userLiveData
     */
    private fun declareDAOCountLiveData(context: Context) {
        if (userDBRoom == null) {
            userDBRoom = UserDatabase.getInstance(context)
        }
        userDBRoom!!.userDAO().getDataCount().observe(this, Observer<Int> {
            tvUserRoomCount.text = "$it"
        })
    }
}
