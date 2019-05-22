package com.binhnk.retrofitwithroom.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "userClientList")
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
    var page: Int,

    var addedInDB: Boolean = false

) : BaseObservable()