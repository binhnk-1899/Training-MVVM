package com.binhnk.demogooglenews.views.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binhnk.demogooglenews.R
import com.binhnk.demogooglenews.adapters.ClassAdapter
import com.binhnk.demogooglenews.controller.RetrofitClient
import com.binhnk.demogooglenews.models.ClassBoard
import com.binhnk.demogooglenews.models.ClassInformation
import com.binhnk.demogooglenews.viewmodels.ClassInformationViewModel
import com.google.firebase.FirebaseApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassInformationFirebaseActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mContext: Context

    private lateinit var im_back: ImageView
    private lateinit var im_create: ImageView
    private lateinit var tv_no_data: TextView

    private lateinit var rv_class: RecyclerView
    private lateinit var mAdapter: ClassAdapter


    var classViewModel: ClassInformationViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystem()
        setContentView(R.layout.activity_class_informaion)

        initUI()
        declareUI()
        initAction()

        classViewModel = ViewModelProviders.of(this).get(ClassInformationViewModel::class.java)
        classViewModel!!.classLiveData.value = ArrayList()
        classViewModel!!.classLiveData.observe(this, Observer<ArrayList<ClassInformation>> {
            mAdapter.updateAdapter(it!!)
            tv_no_data.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })

        loadData()
    }

    private fun initSystem() {
        mContext = this@ClassInformationFirebaseActivity

        FirebaseApp.initializeApp(mContext)
    }

    private fun initUI() {
        im_back = findViewById(R.id.im_back)
        im_create = findViewById(R.id.im_create)
        tv_no_data = findViewById(R.id.tv_no_data)
        rv_class = findViewById(R.id.rv_class)
    }

    private fun declareUI() {
        rv_class.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        mAdapter = ClassAdapter()
        rv_class.adapter = mAdapter
    }

    private fun initAction() {
        im_back.setOnClickListener(this)
        im_create.setOnClickListener(this)
    }

    private fun loadData() {
        RetrofitClient().startEnqueueClassInformation(object : Callback<ArrayList<ClassInformation>> {

            override fun onFailure(call: Call<ArrayList<ClassInformation>>, t: Throwable) {
                Log.e("Ahihi", "Failure")
                t.printStackTrace()
                classViewModel!!.classLiveData.value = ArrayList()
            }

            override fun onResponse(call: Call<ArrayList<ClassInformation>>, response: Response<ArrayList<ClassInformation>>) {
                if (response.isSuccessful) {
                    Log.e("Ahihi", "onResponse success: ${response.body()!!.size}")
                    classViewModel!!.classLiveData.value = response.body()!!
                } else {
                    Log.e("Ahihi", "onResponse not success")
                    classViewModel!!.classLiveData.value = ArrayList()
                }
            }
        })
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.im_back -> {
                onBackPressed()
            }
            R.id.im_create -> {

            }
        }
    }
}