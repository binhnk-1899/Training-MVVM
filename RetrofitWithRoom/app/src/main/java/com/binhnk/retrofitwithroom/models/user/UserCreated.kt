package com.binhnk.retrofitwithroom.models.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserCreated(

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("job")
    @Expose
    var job: String,

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("createdAt")
    @Expose
    var createdAt: String
)