package com.example.covidtracker.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "global_stats")
data class CovidDataModel(
    var confirmed: String?,
    var recovered: String?,
    var deaths: String?,
    var country: String?,
    var population: String?,
    var sq_km_area: String?,
    var life_expectancy: String?,
    var elevation_in_meters: String?,
    var continent: String?,
    var abbreviation: String?,
    var location: String?,
    var iso: String?,
    var capital_city: String?,
    var lat: String?,
    var long: String?,
    var updated: String?
) {
    constructor() : this("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var uid: Int = 0


}

