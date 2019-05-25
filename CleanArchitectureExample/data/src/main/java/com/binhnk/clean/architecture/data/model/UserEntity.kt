package com.binhnk.clean.architecture.data.model

import com.binhnk.clean.architecture.data.base.EntityMapper
import com.binhnk.clean.architecture.data.base.ModelEntity
import com.binhnk.clean.architecture.domain.model.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserEntity(
        @SerializedName("id")
        @Expose
        var id: Int,
        @SerializedName("email")
        @Expose
        var email: String,
        @SerializedName("first_name")
        @Expose
        var firstName: String,
        @SerializedName("last_name")
        @Expose
        var lastName: String,
        @SerializedName("avatar")
        @Expose
        var avatar: String,
        var page: Int = 0,
        var addedInDB: Boolean = false
) : ModelEntity()

class UserEntityMapper : EntityMapper<User, UserEntity> {
    override fun mapToDomain(entity: UserEntity): User = User(
            id = entity.id,
            email = entity.email,
            firstName = entity.firstName,
            lastName = entity.lastName,
            avatar = entity.avatar,
            page = entity.page,
            addedInDB = entity.addedInDB
    )

    override fun mapToEntity(model: User): UserEntity = UserEntity(
            id = model.id,
            email = model.email,
            firstName = model.firstName,
            lastName = model.lastName,
            avatar = model.avatar,
            page = model.page,
            addedInDB = model.addedInDB
    )

}