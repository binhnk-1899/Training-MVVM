package com.binhnk.clean.architecture.data.entities

data class ReviewData(
    var id: String,
    var author: String,
    var content: String? = null
)