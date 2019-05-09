package com.binhnk.demogooglenews.views

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.binhnk.demogooglenews.R
import com.binhnk.demogooglenews.adapters.ArticleAdapter
import com.binhnk.demogooglenews.controller.RetrofitClient
import com.binhnk.demogooglenews.models.Article
import com.binhnk.demogooglenews.models.News
import com.binhnk.demogooglenews.viewmodels.ArticleViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mContext: Context

    private lateinit var im_reload: AppCompatImageView
    private lateinit var tv_load_failed: TextView
    private lateinit var swipe_refresh_layout: SwipeRefreshLayout

    private lateinit var im_put: AppCompatImageView
    private lateinit var im_post: AppCompatImageView
    private lateinit var im_delete: AppCompatImageView

    private lateinit var rv_news: RecyclerView
    private lateinit var mArticleAdapter: ArticleAdapter

    var articleViewModel: ArticleViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this@MainActivity
        setContentView(R.layout.activity_main)

        initUI()
        declareUI()
        loadData()
        initAction()

        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        articleViewModel!!.articleLiveData.value = ArrayList()
        articleViewModel!!.articleLiveData.observe(this, Observer<ArrayList<Article>> {
            mArticleAdapter.updateAdapter(it!!)
            tv_load_failed.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })
    }

    private fun initUI() {
        im_reload = findViewById(R.id.im_reload)
        im_put = findViewById(R.id.im_put)
        im_post = findViewById(R.id.im_post)
        im_delete = findViewById(R.id.im_delete)

        tv_load_failed = findViewById(R.id.tv_load_failed)
        rv_news = findViewById(R.id.rv_news)
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout)
    }

    private fun declareUI() {
        rv_news.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        mArticleAdapter = ArticleAdapter(mContext)
        rv_news.adapter = mArticleAdapter
    }

    private fun loadData() {
        if (!swipe_refresh_layout.isRefreshing) {
            swipe_refresh_layout.isRefreshing = true
        }
        RetrofitClient().startEnqueueGoogleNews(object : Callback<News> {

            override fun onFailure(call: Call<News>, t: Throwable) {
                if (swipe_refresh_layout.isRefreshing) {
                    swipe_refresh_layout.isRefreshing = false
                }
                t.printStackTrace()
                articleViewModel!!.articleLiveData.value = ArrayList()
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (swipe_refresh_layout.isRefreshing) {
                    swipe_refresh_layout.isRefreshing = false
                }
                if (response.isSuccessful) {
                    Log.e("Ahihi", "onResponse success: ${response.body()!!.articles.size}")
                    articleViewModel!!.articleLiveData.value = response.body()!!.articles
                } else {
                    Log.e("Ahihi", "onResponse not success")
                    articleViewModel!!.articleLiveData.value = ArrayList()
                }
            }
        })
    }

    private fun initAction() {
        im_reload.setOnClickListener(this)
        im_put.setOnClickListener(this)
        im_post.setOnClickListener(this)
        im_delete.setOnClickListener(this)

        swipe_refresh_layout.setOnRefreshListener { loadData() }
    }

    override fun onClick(v: View?) {
        val b =
            ActivityOptions.makeCustomAnimation(mContext, R.anim.abc_fade_in, R.anim.abc_fade_out)
                .toBundle()
        when (v!!.id) {
            R.id.im_reload -> {
                loadData()
            }
            R.id.im_put -> {
                startActivity(Intent(mContext, ClassInformationFirebaseActivity::class.java), b)
            }
            R.id.im_post -> {
                startActivity(Intent(mContext, ClassInformationFirebaseActivity::class.java), b)
            }
            R.id.im_delete -> {
                startActivity(Intent(mContext, ClassInformationFirebaseActivity::class.java), b)
            }
        }
    }
}
