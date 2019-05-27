package com.binhnk.clean.architecture.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.binhnk.clean.architecture.R
import com.binhnk.clean.architecture.base.BaseRecyclerAdapter
import com.binhnk.clean.architecture.model.UserItem

//class UserAdapter(
//    private val dataBindingComponent: DataBindingComponent,
//    private val callback: ((UserItem) -> Unit)?
//) : BaseRecyclerAdapter<UserItem>(
//    callBack = object : DiffUtil.ItemCallback<UserItem>() {
//        override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
//            return oldItem.id == newItem.id && oldItem.addedInDB == newItem.addedInDB
//        }
//
//    }
//) {
//    override fun createBinding(parent: ViewGroup, viewType: Int?): ViewDataBinding =
//        DataBindingUtil.inflate<com.binhnk.clean.architecture.databinding.RowUserBinding>(
//            LayoutInflater.from(parent.context),
//            R.layout.row_user, parent, false, dataBindingComponent
//        ).apply {
//            root.setOnClickListener {
//                this.user?.let { item ->
//                    callback?.invoke(item)
//                }
//            }
//        }
//
//    override fun bind(binding: ViewDataBinding, item: UserItem) {
//        if (binding is com.binhnk.clean.architecture.databinding.RowUserBinding) binding.user = item
//    }
//}