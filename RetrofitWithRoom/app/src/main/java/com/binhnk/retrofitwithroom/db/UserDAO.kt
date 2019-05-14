package com.binhnk.retrofitwithroom.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.binhnk.retrofitwithroom.models.user.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM usersLiveData WHERE id = :userId")
    fun getUserByUserId(userId: Int): User?

    @Query("SELECT * FROM usersLiveData WHERE page = :page")
    fun getUsersByPage(page: Int): List<User>

    @Query("SELECT * FROM usersLiveData WHERE first_name LIKE :key OR last_name LIKE :key OR email LIKE :key")
    fun getUserByName(key: String): List<User>

    @Query("SELECT * FROM usersLiveData")
    fun getALlUser(): List<User>

    @Insert
    fun insertUser(vararg users: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM usersLiveData")
    fun deleteAllUser()

    @Update
    fun updateUser(vararg users: User)

    @Query("SELECT COUNT(id) FROM usersLiveData")
    fun getDataCount(): LiveData<Int>
}