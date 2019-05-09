package com.binhnk.demogooglenews.models

import com.google.gson.annotations.SerializedName

class News(
    @SerializedName("status")
    var status: String,
    @SerializedName("totalResults")
    var totalResults: Int,
    @SerializedName("articles")
    var articles: ArrayList<Article>
)