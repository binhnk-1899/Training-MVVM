package com.binhnk.demorecyclerviewadapterbinding

import android.os.Handler
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.util.*


class UserViewModel : BaseObservable() {

//    @get:Bindable
//    var userIds: MutableList<Long> = mutableListOf()
//        private set(value) {
//            field = value
//            notifyPropertyChanged(BR.userIds)
//        }

    @get:Bindable
    var users: MutableList<User> = mutableListOf()
        private set(value) {
            field = value
            notifyPropertyChanged(BR.users)
        }

    @get:Bindable
    var changedPositions: Set<Int> = mutableSetOf()
        private set(value) {
            field = value
            notifyPropertyChanged(BR.changedPositions)
        }

    private val updateInterval = 1000L
    private val updateHandler = Handler()
    private val random = Random()


    private var updateRunnable: Runnable = object : Runnable {
        override fun run() {
            updateList()
            updateHandler.postDelayed(this, updateInterval)
        }
    }

    private fun updateList() {
        val pos1 = random.nextInt(30)
        users[pos1]  = User(random.nextLong(), "User ${random.nextLong()}")

        val pos2 = random.nextInt(30)
        users[pos2]  = User(random.nextLong(), "User ${random.nextLong()}")

        changedPositions = setOf(pos1, pos2)
    }

    fun startUpdates() {
        initList()
        updateHandler.postDelayed(updateRunnable, updateInterval)
    }

    private fun initList() {
        users = MutableList<User>(30) {
            User(random.nextLong(), "User ${random.nextLong()}")
        }
    }

    fun stopUpdates() {
        updateHandler.removeCallbacks(updateRunnable)
    }
}