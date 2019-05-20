package com.binhnk.rxjavaexample.ui.rxjava

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.binhnk.rxjavaexample.R
import com.binhnk.rxjavaexample.base.BaseActivity
import com.binhnk.rxjavaexample.databinding.ActivityPostBinding
import kotlinx.android.synthetic.main.activity_post.*
import org.koin.android.ext.android.inject

class PostActivity : BaseActivity<ActivityPostBinding, PostViewModel>() {

    override val viewModel: PostViewModel by inject()
    override val layoutId: Int
        get() = R.layout.activity_post

    private val mAdapter: PostAdapter by lazy {
        PostAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRv()

        viewModel.run {
            loading.observe(this@PostActivity, Observer {
                swipe_refresh.isRefreshing = it
            })
        }
    }

    private fun initRv() {
        rv_posts.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_posts.adapter = mAdapter
    }
}