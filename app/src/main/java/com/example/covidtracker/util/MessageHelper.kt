package com.example.covidtracker.util

import android.content.Context
import android.widget.Toast

object ToastHelper {


    fun smallMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun bigMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}