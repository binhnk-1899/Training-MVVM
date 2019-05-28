package com.binhnk.clean.architecture.domain.usecase.user

import com.binhnk.clean.architecture.domain.model.User
import com.binhnk.clean.architecture.domain.repository.UserRepository
import com.binhnk.clean.architecture.domain.usecase.UseCase
import io.reactivex.Single

class InsertToDBUseCase(
    private val userRepository: UserRepository
) : UseCase<InsertToDBUseCase.Params, Single<Long>>() {

    override fun createObservable(params: Params?): Single<Long> {
        params?.let {
            return userRepository.insertUserToDB(user = params.user)
        }
        return Single.error(Throwable("Insert Failed"))
    }

    override fun onCleared() {

    }


    data class Params(val user: User)

}