package com.binhnk.rxjavaexample


interface Subject {
    fun registerObserver(o: Observer)
    fun removeObserver(o: Observer)
    fun notifyObservers()
}

interface Observer {
    fun updateTime(currentTimeMills: Long)
}

interface DisplayElement {
    fun display()
}