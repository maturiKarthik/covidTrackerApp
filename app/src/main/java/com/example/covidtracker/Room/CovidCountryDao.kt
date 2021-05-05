package com.example.covidtracker.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.covidtracker.model.CovidDataModel

@Dao
interface CovidCountryDao {

    @Insert
    suspend fun insertAll(data: List<CovidDataModel?>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(covidDataModel: CovidDataModel)

    @Query("DELETE FROM global_stats ")
    suspend fun deleteAll()

    @Query("SELECT * FROM global_stats")
    suspend fun getAllStats(): List<CovidDataModel>

    @Query("SELECT * FROM global_stats WHERE `country` LIKE  :country")
    suspend fun searchByCountry(country: String): List<CovidDataModel>
}