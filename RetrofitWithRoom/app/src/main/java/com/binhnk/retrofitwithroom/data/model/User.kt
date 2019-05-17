package com.binhnk.retrofitwithroom.data.model

import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.Glide
import androidx.databinding.BindingAdapter

@Entity(tableName = "usersLiveData")
class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    @Bindable
    var id: Int,

    @ColumnInfo(name = "email")
    @SerializedName("email")
    @Expose
    @Bindable
    var email: String,

    @ColumnInfo(name = "first_name")
    @SerializedName("first_name")
    @Expose
    @Bindable
    var firstName: String,

    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    @Expose
    @Bindable
    var lastName: String,

    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    @Expose
    @Bindable
    var avatar: String,

    @ColumnInfo(name = "page")
    @SerializedName("page")
    @Expose
    var page: Int
) : BaseObservable() {

    @Bindable
    fun getFullName() : String {
        return "$firstName $lastName"
    }

    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(view)
    }
}