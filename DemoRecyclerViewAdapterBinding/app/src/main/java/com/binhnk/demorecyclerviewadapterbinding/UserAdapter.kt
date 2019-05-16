package com.binhnk.demorecyclerviewadapterbinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserHolder>(), BindableAdapter<User> {

    override fun setData(items: List<User>) {
        userIds = items
        notifyDataSetChanged()
    }

    override fun changedPositions(positions: Set<Int>) {
        positions.forEach(this::notifyItemChanged)
    }

    var userIds = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount() = userIds.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(userIds[position])
    }

    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            itemView.userID.text = "User id: ${user.id}"
            itemView.userName.text = "User id: ${user.name}"
        }
    }

}