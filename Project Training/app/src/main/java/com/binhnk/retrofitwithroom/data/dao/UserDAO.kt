package com.binhnk.retrofitwithroom.data.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.binhnk.retrofitwithroom.data.model.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface UserDAO {
    @Query("SELECT * FROM userClientList WHERE id = :userId")
    fun getUserByUserId(userId: Int): User?

    @Query("SELECT * FROM userClientList WHERE page = :page")
    fun getUsersByPage(page: Int): List<User>

    @Query("SELECT * FROM userClientList WHERE first_name LIKE :key OR last_name LIKE :key OR email LIKE :key")
    fun getUserByName(key: String): List<User>

    @Query("SELECT * FROM userClientList")
    fun getALlUser(): Flowable<List<User>>

    @Insert
    fun insertUserByRx(user: User): Long

    @Delete
    fun deleteUser(user: User): Int

    @Query("DELETE FROM userClientList")
    fun deleteAllUser()

    @Update
    fun updateUser(vararg users: User)

    @Query("SELECT COUNT(id) FROM userClientList")
    fun getDataCount(): LiveData<Int>
}