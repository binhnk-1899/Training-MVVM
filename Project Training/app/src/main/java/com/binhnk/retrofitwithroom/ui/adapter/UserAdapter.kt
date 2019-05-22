package com.binhnk.retrofitwithroom.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.data.model.User
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(
        private val mContext: Context,
        private val showChecking: Boolean,
        private val mCallback: Callback
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val dataList: ArrayList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_user,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holderUser: UserViewHolder, position: Int) {
        val mObj = dataList[position]

        holderUser.tvUserName.text = String.format("%s %s", mObj.firstName, mObj.lastName)
        holderUser.tvEmail.text = mObj.email
        Glide.with(mContext)
                .load(mObj.avatar)
                .placeholder(R.drawable.bg_place_holder)
                .centerCrop()
                .into(holderUser.imAvatar)

        if (showChecking) {
            if (mObj.addedInDB) {
                holderUser.tvUserName.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                holderUser.imChecked.visibility = View.VISIBLE
            } else {
                holderUser.tvUserName.setTextColor(Color.BLACK)
                holderUser.imChecked.visibility = View.INVISIBLE
            }
        } else {
            holderUser.tvUserName.setTextColor(Color.BLACK)
            holderUser.imChecked.visibility = View.INVISIBLE
        }

        holderUser.itemView.setOnClickListener {
            mCallback.onItemClicked(mObj)
        }

        holderUser.itemView.setOnLongClickListener {
            mCallback.onItemLongClicked(mObj)
            true
        }
    }

    override fun onBindViewHolder(
            holder: UserViewHolder,
            position: Int,
            payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            for (s in payloads) {
                if (s == "update_check") {
                    val mObj = dataList[position]
                    if (showChecking) {
                        if (mObj.addedInDB) {
                            holder.tvUserName.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                            holder.imChecked.visibility = View.VISIBLE
                        } else {
                            holder.tvUserName.setTextColor(Color.BLACK)
                            holder.imChecked.visibility = View.INVISIBLE
                        }
                    } else {
                        holder.tvUserName.setTextColor(Color.BLACK)
                        holder.imChecked.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    /**
     * get item position by userID
     */
    private fun getItemPosition(userID: Int): Int {
        if (dataList.isNullOrEmpty()) {
            return -1
        } else {
            for (i in 0 until dataList.size) {
                if (dataList[i].id == userID) {
                    return i
                }
            }
            return -1
        }
    }

    /**
     * update new userClientList for adapter
     */
    fun updateAdapter(mNewLst: ArrayList<User>) {
        val callback = DiffUtil.calculateDiff(UserDiffUtils(dataList, mNewLst))

        dataList.clear()
        dataList.addAll(mNewLst)
        callback.dispatchUpdatesTo(this)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: TextView = itemView.findViewById(R.id.tv_user_name)
        val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        val imAvatar: CircleImageView = itemView.findViewById(R.id.im_avatar)
        val imChecked: ImageView = itemView.findViewById(R.id.im_checked)
    }

    interface Callback {
        fun onItemClicked(mUserClicked: User)
        fun onItemLongClicked(mUserClicked: User)
    }
}