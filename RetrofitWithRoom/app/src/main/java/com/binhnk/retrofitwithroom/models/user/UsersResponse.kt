package com.binhnk.retrofitwithroom.models.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class UsersResponse(
    @SerializedName("page")
    @Expose
    var page: Int,
    @SerializedName("per_page")
    @Expose
    var perPage: Int,
    @SerializedName("total")
    @Expose
    var total: Int,
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int,
    @SerializedName("data")
    @Expose
    var data: ArrayList<Datum>
)