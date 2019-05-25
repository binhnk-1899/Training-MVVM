package com.binhnk.clean.architecture.model

data class UserItem(
        var id: Int,
        var email: String,
        var firstName: String,
        var lastName: String,
        var avatar: String,
        var page: Int,
        var addedInDB: Boolean = false
)