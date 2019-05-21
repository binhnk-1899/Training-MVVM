package com.binhnk.retrofitwithroom.data.model

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