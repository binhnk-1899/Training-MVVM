package com.binhnk.retrofitwithroom.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.binhnk.retrofitwithroom.data.constants.Constants
import com.binhnk.retrofitwithroom.data.dao.UserDAO
import com.binhnk.retrofitwithroom.data.model.User


@Database(entities = [User::class], version = Constants.DATABASE_VERSION, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDAO

}