package com.example.covidtracker.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.covidtracker.model.CovidStateModel

@Dao
interface CovidStateDao {
    @Insert
    suspend fun insertAll(data: List<CovidStateModel?>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(covidDataModel: CovidStateModel)

    @Query("DELETE FROM state_stats ")
    suspend fun deleteAll()

    @Query("SELECT * FROM state_stats WHERE `country` =  :country AND `state` LIKE  :state")
    suspend fun searchBySate(state: String, country: String): List<CovidStateModel>

    @Query("SELECT * FROM state_stats WHERE `country` =  :country")
    suspend fun searchByCountry(country: String): List<CovidStateModel>

}
