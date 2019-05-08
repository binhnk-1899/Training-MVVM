package com.binhnk.demogooglenews.models

import com.google.gson.annotations.SerializedName

class Article(
    @SerializedName("source")
    var source: Source,
    @SerializedName("author")
    var author: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("urlToImage")
    var urlToImage: String,
    @SerializedName("publishAt")
    var publishAt: String,
    @SerializedName("content")
    var content: String
)