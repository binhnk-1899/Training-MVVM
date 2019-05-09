package com.binhnk.retrofitexample.models.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum (
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("email")
    @Expose
    var email: String,
    @SerializedName("first_name")
    @Expose
    var firstName: String,
    @SerializedName("last_name")
    @Expose
    var lastName: String,
    @SerializedName("avatar")
    @Expose
    var avatar: String
)