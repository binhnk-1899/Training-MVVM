package com.binhnk.retrofitwithroom.data.pref

import android.content.Context
import com.google.gson.Gson

class AppPrefs constructor(
    context: Context,
    val gson: Gson
) : PrefHelper {

    companion object {
        private const val FIRST_RUN = "first_run"
    }

    private val sharedPreferences = context.getSharedPreferences(
        context.packageName,
        Context.MODE_PRIVATE
    )

    override fun isFirstRun(): Boolean {
        val isFirstRun = sharedPreferences.getBoolean(FIRST_RUN, true)
        if (isFirstRun) {
            sharedPreferences.edit().putBoolean(FIRST_RUN, false).apply()
        }
        return isFirstRun
    }

    override fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}