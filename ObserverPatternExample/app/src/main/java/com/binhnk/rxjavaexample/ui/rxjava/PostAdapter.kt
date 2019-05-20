package com.binhnk.rxjavaexample.ui.rxjava

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.binhnk.rxjavaexample.R
import com.binhnk.rxjavaexample.models.Post
import kotlinx.android.synthetic.main.row_post.view.*

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    var mLst: List<Post> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_post,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mLst.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mLst[position], position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(mObj: Post, position: Int) {
            itemView.tv_title.text = mObj.title
            itemView.tv_body.text = mObj.body

            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context, if (position % 2 == 0) {
                        R.color.colorBackground0
                    } else {
                        R.color.colorBackground1
                    }
                )
            )
        }

    }
}