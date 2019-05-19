package com.binhnk.rxjavaexample.ui.rxjava

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.binhnk.rxjavaexample.R
import com.binhnk.rxjavaexample.models.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    var mLst: List<Post> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false))
    }

    override fun getItemCount(): Int {
        return mLst.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mLst[position], position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_title: TextView by lazy { itemView.findViewById(R.id.tv_title) as TextView }
        val tv_body: TextView by lazy { itemView.findViewById(R.id.tv_body) as TextView }
        val tv_author: TextView by lazy { itemView.findViewById(R.id.tv_author) as TextView }

        fun bind(mObj: Post, position: Int) {
            tv_title.text = mObj.title
            tv_body.text = mObj.body
            tv_author.text = mObj.userId.toString()
        }

    }
}