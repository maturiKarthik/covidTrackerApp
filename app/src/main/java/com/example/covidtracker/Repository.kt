package com.example.covidtracker

import android.content.Context
import com.example.covidtracker.Retrofit.RetroFitHelper
import com.example.covidtracker.Room.RoomDatabaseHelper
import com.example.covidtracker.SharedPreference.SharedPrefHelper

object Repository {
    lateinit var retroFitHelper: RetroFitHelper
    lateinit var roomDatabaseHelper: RoomDatabaseHelper
    lateinit var sharedPrefHelper: SharedPrefHelper

    fun plant(context: Context) {
        retroFitHelper = RetroFitHelper.invoke()
        roomDatabaseHelper = RoomDatabaseHelper.invoke(context)
        sharedPrefHelper = SharedPrefHelper.invoke(context)
    }
}