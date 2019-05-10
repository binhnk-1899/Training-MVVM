package com.binhnk.retrofitwithroom.ui.activities

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
import com.binhnk.retrofitwithroom.models.user.UsersResponse
import com.binhnk.retrofitwithroom.viewmodel.UserResponseViewModel
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mContext: Context

    // UI
    private lateinit var swipe_refresh_layout: SwipeRefreshLayout
    private lateinit var rv_user: RecyclerView
    private lateinit var mAdapter: UserAdapter

    private lateinit var edt_page_number: EditText
    private lateinit var btn_get_data: Button

    private var mUserResponseVM: UserResponseViewModel? = null

    private var pageValue = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this@MainActivity
        setContentView(R.layout.activity_main)


        declareUserLiveData()

        initUI()
        declareUI()
        initAction()

        startLoadUserData()
    }

    /**
     * declare user live data
     */
    private fun declareUserLiveData() {
        if (mUserResponseVM == null) {
            mUserResponseVM = ViewModelProviders.of(this).get(UserResponseViewModel::class.java)
        }
        mUserResponseVM!!.userResponseLiveData.postValue(null)
        mUserResponseVM!!.userResponseLiveData.observe(this, Observer<UsersResponse> {
            val newData = if (it != null) {
                Log.e("Ahihi", "Size: ${it.data.size}")
                it.data
            } else {
                Log.e("Ahihi", "Live Data null")
                ArrayList()
            }
            mAdapter.updateAdapter(newData)
        })
    }

    /**
     * init UI
     */
    private fun initUI() {
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout)
        rv_user = findViewById(R.id.rv_user)
        edt_page_number = findViewById(R.id.edt_page_number)
        btn_get_data = findViewById(R.id.btn_get_data)
    }

    /**
     * declare UI
     */
    private fun declareUI() {
        mAdapter = UserAdapter(mContext)
        rv_user.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rv_user.adapter = mAdapter
    }

    private fun initAction() {
        swipe_refresh_layout.setOnRefreshListener {
            startLoadUserData()
        }
        btn_get_data.setOnClickListener {
            if (edt_page_number.text.toString() != "") {
                pageValue = edt_page_number.text.toString().toInt()
                startLoadUserData()
            } else {
                Toast.makeText(mContext, "Please enter page number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * start load user data
     */
    private fun startLoadUserData() {
        Handler().post {
            if (!swipe_refresh_layout.isRefreshing) {
                swipe_refresh_layout.isRefreshing = true
            }
        }
        ClientController().requestGetListUser(
            pageValue,
            object : retrofit2.Callback<UsersResponse> {
                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    Toast.makeText(mContext, "Request failure", Toast.LENGTH_SHORT).show()
                    mUserResponseVM!!.userResponseLiveData.postValue(null)
                    swipe_refresh_layout.isRefreshing = false
                }

                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
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
}
