package com.example.covidtracker.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.covidtracker.R
import com.example.covidtracker.Repository

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Repository.plant(applicationContext)
        onNavigateUp()
        setupBackButtonOnToolbar(this, R.id.fragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    private fun setupBackButtonOnToolbar(activity: AppCompatActivity, container: Int) {
        navController = Navigation.findNavController(activity, container)
        NavigationUI.setupActionBarWithNavController(activity, navController)

    }
}
