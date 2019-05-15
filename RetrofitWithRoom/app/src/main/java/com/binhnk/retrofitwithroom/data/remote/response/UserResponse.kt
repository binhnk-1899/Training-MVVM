package com.binhnk.retrofitwithroom.data.remote.response

import com.binhnk.retrofitwithroom.data.model.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class UserResponse(
    @SerializedName("page")
    @Expose var page: Int,
    @SerializedName("per_page")
    @Expose var perPage: Int,
    @SerializedName("total")
    @Expose var total: Int,
    @SerializedName("total_pages")
    @Expose var totalPages: Int,
    @SerializedName("data")
    @Expose var users: ArrayList<User>
) {

    fun setPageForUser() {
        if (users.isNotEmpty()) {
            for (user in users) {
                user.page = page
            }
        }
    }
}