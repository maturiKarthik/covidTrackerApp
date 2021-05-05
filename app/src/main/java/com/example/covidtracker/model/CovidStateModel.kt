package com.example.covidtracker.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "state_stats")
data class CovidStateModel(
    var lat: String?,
    var longitude: String?,
    var confirmed: String?,
    var recovered: String?,
    var deaths: String?,
    var updated: String?,
    var country: String?,
    var state: String?
) {
    constructor() : this("", "", "", "", "", "", "", "")

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var uid: Int = 0
}