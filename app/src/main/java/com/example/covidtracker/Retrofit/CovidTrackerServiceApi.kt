package com.example.covidtracker.Retrofit


import com.example.covidtracker.model.CovidDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// When your Keys in json are dynamic
interface CovidTrackerServiceApi {
    @GET("cases")
    fun getCountryTracker(@Query("country") query: String): Call<Map<String, Map<String, CovidDataModel>>>
}