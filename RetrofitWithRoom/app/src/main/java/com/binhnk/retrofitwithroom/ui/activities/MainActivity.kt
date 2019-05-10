package com.binhnk.retrofitwithroom.ui.activities

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.adapters.UserAdapter
import com.binhnk.retrofitwithroom.client.ClientController
import com.binhnk.retrofitwithroom.models.user.User
import com.binhnk.retrofitwithroom.models.user.UserResponse
import com.binhnk.retrofitwithroom.room.UserDatabase
import com.binhnk.retrofitwithroom.viewmodel.UserResponseViewModel
import com.binhnk.retrofitwithroom.viewmodel.UserViewModel
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mContext: Context

    // UI
    private lateinit var swipe_refresh_layout: SwipeRefreshLayout
    private lateinit var rv_user: RecyclerView
    private lateinit var mAdapter: UserAdapter

    private lateinit var tv_datum_dao_count: TextView
    private lateinit var edt_page_number: EditText
    private lateinit var btn_get_data: Button

    private var mUserResponseVM: UserResponseViewModel? = null
    private var mUserVM: UserViewModel? = null
    private var userDBRoom: UserDatabase? = null
    private var pageValue = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this@MainActivity
        setContentView(R.layout.activity_main)

        declareUserLiveData()
        declareUserResponseLiveData()
        declareDAOCountLiveData(mContext)

        initUI()
        declareUI()
        initAction()

        startLoadUserDataFromAPI()
    }

    /**
     * declare user live data
     */
    private fun declareUserLiveData() {
        if (mUserVM == null) {
            mUserVM = ViewModelProviders.of(this).get(UserViewModel::class.java)
        }
        mUserVM!!.userLiveData.observe(this, Observer<ArrayList<User>> {
            val newData = if (it != null) {
                Log.e("Ahihi", "Size: ${it.size}")
                it
            } else {
                Log.e("Ahihi", "Live Data null")
                ArrayList()
            }
            mAdapter.updateAdapter(newData)
        })
    }

    /**
     * declare user response live data
     */
    private fun declareUserResponseLiveData() {
        if (mUserResponseVM == null) {
            mUserResponseVM = ViewModelProviders.of(this).get(UserResponseViewModel::class.java)
        }
        mUserResponseVM!!.userResponseLiveData.postValue(null)
        mUserResponseVM!!.userResponseLiveData.observe(this, Observer<UserResponse> {
            if (it != null) {
                Log.e("Ahihi", "Size: ${it.users.size}")
                it.setPageForUser()
                mUserVM!!.userLiveData.postValue(it.users)
            } else {
                Log.e("Ahihi", "Live Data null")
                Thread(Runnable {
                    loadUserDataFromRoom(pageValue)
                }).start()
            }

        })
    }

    /**
     * init UI
     */
    private fun initUI() {
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout)
        rv_user = findViewById(R.id.rv_user)
        tv_datum_dao_count = findViewById(R.id.tv_datum_dao_count)
        edt_page_number = findViewById(R.id.edt_page_number)
        btn_get_data = findViewById(R.id.btn_get_data)
    }

    /**
     * declare UI
     */
    private fun declareUI() {
        mAdapter = UserAdapter(mContext, object : UserAdapter.Callback {
            override fun onItemLongClicked(mUserClicked: User) {
                Thread(Runnable {
                    if (userDBRoom == null) {
                        userDBRoom = UserDatabase.getInstance(mContext)
                    }
                }).start()
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
                            Toast.makeText(mContext, "${tempUser!!.page}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }).start()

            }
        })
        rv_user.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rv_user.adapter = mAdapter
    }

    /**
     * init action
     */
    private fun initAction() {
        swipe_refresh_layout.setOnRefreshListener {
            startLoadUserDataFromAPI()
        }
        btn_get_data.setOnClickListener {
            if (edt_page_number.text.toString() != "") {
                pageValue = edt_page_number.text.toString().toInt()
                startLoadUserDataFromAPI()
            } else {
                Toast.makeText(mContext, "Please enter page number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * start load user users
     */
    private fun startLoadUserDataFromAPI() {
        Handler().post {
            if (!swipe_refresh_layout.isRefreshing) {
                swipe_refresh_layout.isRefreshing = true
            }
        }
        ClientController().requestGetListUser(
            pageValue,
            object : retrofit2.Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(mContext, "Request failure", Toast.LENGTH_SHORT).show()
                    mUserResponseVM!!.userResponseLiveData.postValue(null)
                    swipe_refresh_layout.isRefreshing = false
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
                    swipe_refresh_layout.isRefreshing = false
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
     * declare datum DAO count live users
     */
    private fun declareDAOCountLiveData(context: Context) {
        if (userDBRoom == null) {
            userDBRoom = UserDatabase.getInstance(context)
        }
        userDBRoom!!.userDAO().getDataCount().observe(this, Observer<Int> {
            tv_datum_dao_count.text = "$it"
        })
    }
}
