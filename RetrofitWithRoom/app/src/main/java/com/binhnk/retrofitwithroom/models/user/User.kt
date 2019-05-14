package com.binhnk.retrofitwithroom.models.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "usersLiveData")
class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int,

    @ColumnInfo(name = "email")
    @SerializedName("email")
    @Expose
    var email: String,

    @ColumnInfo(name = "first_name")
    @SerializedName("first_name")
    @Expose
    var firstName: String,

    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    @Expose
    var lastName: String,

    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    @Expose
    var avatar: String,

    @ColumnInfo(name = "page")
    @SerializedName("page")
    @Expose
    var page: Int
)