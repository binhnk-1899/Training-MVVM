package com.binhnk.retrofitwithroom.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.binhnk.retrofitwithroom.models.user.User
import com.binhnk.retrofitwithroom.data.db.UserDatabase.Companion.DATABASE_VERSION
import com.binhnk.retrofitwithroom.data.dao.UserDAO


@Database(entities = [User::class], version = DATABASE_VERSION, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDAO

    companion object {
        private var INSTANCE: UserDatabase? = null

        const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "DatumDatabase.db"

        fun getInstance(context: Context): UserDatabase {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext, UserDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}