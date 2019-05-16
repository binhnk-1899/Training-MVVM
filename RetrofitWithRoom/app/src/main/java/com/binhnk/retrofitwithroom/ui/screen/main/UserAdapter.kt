package com.binhnk.retrofitwithroom.ui.screen.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binhnk.retrofitwithroom.data.model.User
import com.binhnk.retrofitwithroom.databinding.RowUserBinding
import com.binhnk.retrofitwithroom.ui.adapters.UserDiffUtils


class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val mList: ArrayList<User> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: RowUserBinding =
            DataBindingUtil.inflate(
                layoutInflater!!,
                com.binhnk.retrofitwithroom.R.layout.row_user,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.user = mList[position]
    }

    /**
     * update new usersLiveData for adapter
     */
    fun updateAdapter(mNewLst: ArrayList<User>) {
        val callback = DiffUtil.calculateDiff(UserDiffUtils(mList, mNewLst))

        mList.clear()
        mList.addAll(mNewLst)
        callback.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root)

}