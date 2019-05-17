package com.binhnk.rxjavaexample

import java.util.concurrent.TimeUnit

class TimeData(var ms: Long) : Subject {

    private val observers: ArrayList<Observer> = ArrayList()

    override fun registerObserver(o: Observer) {
        observers.add(o)
    }

    override fun removeObserver(o: Observer) {
        val i = observers.indexOf(o)
        if (i >= 0) {
            observers.removeAt(i)
        }
    }

    override fun notifyObservers() {
        for (i in 0 until observers.size) {
            observers[i].updateTime(ms)
        }
    }

    fun measurementsChanged() {
        notifyObservers()
    }

    fun setMeasurements(tempMs: Long) {
        this.ms = tempMs
        measurementsChanged()
    }

    fun convertTimeToDate() :String{
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(this.ms),
                TimeUnit.MILLISECONDS.toMinutes(this.ms) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this.ms)),
                TimeUnit.MILLISECONDS.toSeconds(this.ms) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.ms)))
    }
}