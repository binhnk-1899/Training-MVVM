package com.binhnk.retrofitwithroom.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.binhnk.retrofitwithroom.models.user.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserByUserId(userId: Int): User?

    @Query("SELECT * FROM users WHERE page = :page")
    fun getUsersByPage(page: Int): List<User>

    @Query("SELECT * FROM users WHERE first_name LIKE :key OR last_name LIKE :key OR email LIKE :key")
    fun getUserByName(key: String): List<User>

    @Query("SELECT * FROM users")
    fun getALlUser(): List<User>

    @Insert
    fun insertUser(vararg users: User)

//    @Query
//    fun checkUser(vararg users: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM users")
    fun deleteAllUser()

    @Update
    fun updateUser(vararg users: User)

    @Query("SELECT COUNT(id) FROM users")
    fun getDataCount(): LiveData<Int>
}