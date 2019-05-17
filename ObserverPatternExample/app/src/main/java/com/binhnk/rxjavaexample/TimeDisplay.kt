package com.binhnk.rxjavaexample

import android.util.Log

class TimeDisplay(private val data: TimeData) : Observer, DisplayElement {
    init {
        data.registerObserver(this)
    }

    override fun updateTime(currentTimeMills: Long) {
        display()
    }

    override fun display() {
        Log.e("Ahihi", data.convertTimeToDate())
    }
}