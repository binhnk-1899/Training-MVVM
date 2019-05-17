package com.binhnk.retrofitwithroom.data.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.binhnk.retrofitwithroom.data.model.User

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

    @Insert(onConflict = OnConflictStrategy.IGNORE) // IGNORE was return value <= 0
    fun insertUser(user: User): Long

    @Delete
    fun deleteUser(user: User): Int

    @Query("DELETE FROM usersLiveData")
    fun deleteAllUser()

    @Update
    fun updateUser(vararg users: User)

    @Query("SELECT COUNT(id) FROM usersLiveData")
    fun getDataCount(): LiveData<Int>
}