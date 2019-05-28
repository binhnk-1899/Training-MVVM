package com.binhnk.clean.architecture.domain.repository

import com.binhnk.clean.architecture.domain.model.User
import io.reactivex.Single

interface UserRepository : Repository {
    fun getUsersUsingRx(page: Int? = 0): Single<List<User>>

    fun insertUserToDB(user: User): Single<Long>
}