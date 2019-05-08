package com.binhnk.demogooglenews.adapters

import androidx.recyclerview.widget.DiffUtil
import com.binhnk.demogooglenews.models.Article

class ArticleDiffUtils(
    private val mOldList: ArrayList<Article>,
    private val mNewList: ArrayList<Article>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList[oldItemPosition].url == mNewList[newItemPosition].url
    }

    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList[oldItemPosition].content == mNewList[newItemPosition].content
    }

}