package com.binhnk.retrofitwithroom.data.pref

interface PrefHelper {

    fun isFirstRun(): Boolean

    fun remove(key: String)

    fun clear()

}