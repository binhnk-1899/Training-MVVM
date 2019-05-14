package com.binhnk.retrofitwithroom.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.binhnk.retrofitwithroom.models.user.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM userLiveData WHERE id = :userId")
    fun getUserByUserId(userId: Int): User?

    @Query("SELECT * FROM userLiveData WHERE page = :page")
    fun getUsersByPage(page: Int): List<User>

    @Query("SELECT * FROM userLiveData WHERE first_name LIKE :key OR last_name LIKE :key OR email LIKE :key")
    fun getUserByName(key: String): List<User>

    @Query("SELECT * FROM userLiveData")
    fun getALlUser(): List<User>

    @Insert
    fun insertUser(vararg users: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM userLiveData")
    fun deleteAllUser()

    @Update
    fun updateUser(vararg users: User)

    @Query("SELECT COUNT(id) FROM userLiveData")
    fun getDataCount(): LiveData<Int>
}