package com.binhnk.clean.architecture.domain.entities

data class ReviewEntity (
    var id: String,
    var author: String,
    var content: String? = null
)