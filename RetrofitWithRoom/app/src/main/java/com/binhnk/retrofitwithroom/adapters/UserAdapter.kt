package com.binhnk.retrofitwithroom.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.binhnk.retrofitwithroom.R
import com.binhnk.retrofitwithroom.models.user.User
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(
    private val mContext: Context,
    private val mCallback: Callback
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val dataList: ArrayList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_user,
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

        holderUser.itemView.setOnClickListener {
            mCallback.onItemClicked(mObj)
        }
        holderUser.itemView.setOnLongClickListener {
            mCallback.onItemLongClicked(mObj)
            true
        }
    }

    /**
     * update new users for adapter
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
    }

    interface Callback {
        fun onItemClicked(mUserClicked: User)
        fun onItemLongClicked(mUserClicked: User)
    }
}