package com.binhnk.retrofitwithroom.adapters

import androidx.recyclerview.widget.DiffUtil
import com.binhnk.retrofitwithroom.models.user.User

class UserDiffUtils(
    private val oldLst: ArrayList<User>,
    private val newLst: ArrayList<User>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

    override fun getOldListSize(): Int {
        return oldLst.size
    }

    override fun getNewListSize(): Int {
        return newLst.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldLst[oldItemPosition].id == newLst[newItemPosition].id
    }
}