package com.binhnk.demogooglenews.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binhnk.demogooglenews.R
import com.binhnk.demogooglenews.models.Article
import com.bumptech.glide.Glide

class ArticleAdapter (private val mContext: Context): RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private var articleList: ArrayList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_news,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mObjects = articleList[position]
        Glide.with(mContext).load(mObjects.urlToImage).into(holder.im_thumbnail)
        holder.tv_title.text = mObjects.title
        holder.tv_description.text = mObjects.description
    }

    fun updateAdapter(newList: ArrayList<Article>) {
        val result = DiffUtil.calculateDiff(ArticleDiffUtils(articleList, newList))

        articleList.clear()
        articleList.addAll(newList)

        result.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val im_thumbnail: ImageView = itemView.findViewById(R.id.im_thumbnail)
        val tv_title: TextView = itemView.findViewById(R.id.tv_title)
        val tv_description: TextView = itemView.findViewById(R.id.tv_description)
    }
}