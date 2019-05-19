package com.binhnk.rxjavaexample.ui.rxjava

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binhnk.rxjavaexample.R
import com.binhnk.rxjavaexample.data.client.RetrofitClient
import com.binhnk.rxjavaexample.data.remote.api.PostApi
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava_retrofit.*

class RxJavaWithRetrofitActivity : AppCompatActivity() {

    private var mCompositeDisposable: CompositeDisposable? = null
    private val mAdapter: PostAdapter by lazy {
        PostAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava_retrofit)

        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        val retrofit = RetrofitClient.getInstance()
        val postAPI = retrofit.create(PostApi::class.java)

        rv_posts.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_posts.adapter = mAdapter

        mCompositeDisposable!!.add(postAPI.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                mAdapter.mLst = it
                mAdapter.notifyDataSetChanged()
            })
    }
}