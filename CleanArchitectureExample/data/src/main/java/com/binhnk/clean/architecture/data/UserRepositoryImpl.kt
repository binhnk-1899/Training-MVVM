package com.binhnk.clean.architecture.data

import com.binhnk.clean.architecture.data.model.UserEntityMapper
import com.binhnk.clean.architecture.data.remote.api.UserApi
import com.binhnk.clean.architecture.domain.model.User
import com.binhnk.clean.architecture.domain.repository.UserRepository
import io.reactivex.Single

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userEntityMapper: UserEntityMapper
) : UserRepository {

    override fun getUsersUsingRx(page: Int?): Single<List<User>> {
        return userApi.getAllUsersUsingRx(page = if (page == null) "0" else "$page")
            .map { response ->
                response.users.map { userEntityMapper.mapToDomain(it) }
            }
            .doOnError { Throwable("Page not found!") }
    }

}