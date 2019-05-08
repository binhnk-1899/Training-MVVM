package com.binhnk.demogooglenews.views

import android.content.Context
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
import com.binhnk.demogooglenews.R
import com.binhnk.demogooglenews.adapters.ArticleAdapter
import com.binhnk.demogooglenews.controller.ConnectionController
import com.binhnk.demogooglenews.models.Article
import com.binhnk.demogooglenews.models.News
import com.binhnk.demogooglenews.viewmodels.ArticleViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mContext: Context

    private lateinit var im_reload: AppCompatImageView
    private lateinit var tv_load_failed: TextView

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
        articleViewModel!!.mResponseLiveData.value = ArrayList()
        articleViewModel!!.mResponseLiveData.observe(this, Observer<ArrayList<Article>> {
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
        tv_load_failed = findViewById(R.id.tv_load_failed)
        rv_news = findViewById(R.id.rv_news)
    }

    private fun declareUI() {
        rv_news.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        mArticleAdapter = ArticleAdapter(mContext)
        rv_news.adapter = mArticleAdapter
    }

    private fun loadData() {
        ConnectionController().start(object : Callback<News> {

            override fun onFailure(call: Call<News>, t: Throwable) {
//                Log.e("Ahihi", "onFailure: ${t.printStackTrace()}")
                t.printStackTrace()
                articleViewModel!!.mResponseLiveData.value = ArrayList()
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    Log.e("Ahihi", "onResponse success: ${response.body()!!.articles.size}")
                    articleViewModel!!.mResponseLiveData.value = response.body()!!.articles
                } else {
                    Log.e("Ahihi", "onResponse not success")
                    articleViewModel!!.mResponseLiveData.value = ArrayList()
                }
            }
        })
    }

    private fun initAction() {
        im_reload.setOnClickListener {
            loadData()
        }
    }
}
