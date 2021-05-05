package com.example.covidtracker.SharedPreference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Job

class SharedPrefHelper {

    companion object {

        var instance: SharedPrefHelper? = null
        var LOCK = Job()
        var preference: SharedPreferences? = null
        const val TIME_UPDATE = "LAST_UPDATE"

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: build(context).also {
                instance = it
            }
        }

        private fun build(context: Context): SharedPrefHelper {
            preference = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPrefHelper()
        }
    }

    fun setLastUpdateTime(time: Long) {
        preference?.let {
            it.edit(commit = true) {
                putLong(TIME_UPDATE, time)
            }
        }
    }

    fun getLastUpdateTime(): Long?{
      return  preference?.getLong(TIME_UPDATE,0L)
    }
}