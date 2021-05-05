package com.example.covidtracker.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.covidtracker.model.CovidDataModel
import com.example.covidtracker.model.CovidStateModel

@Database(
    entities = [CovidDataModel::class, CovidStateModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun covidDao(): CovidCountryDao
    abstract fun covidStateDao(): CovidStateDao
}

class RoomDatabaseHelper {

    companion object {
        private var instance: RoomDatabaseHelper? = null
        private var covidDao: CovidCountryDao? = null
        private var covidStateDao: CovidStateDao? = null
        private val LOCK = Any()
        private const val DB_NAME = "covid-tracker-system"

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: build(context).also {
                instance = it
            }
        }


        private fun build(context: Context): RoomDatabaseHelper {
            val database =
                Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries().build()
            covidDao = database.covidDao()
            covidStateDao = database.covidStateDao()
            return RoomDatabaseHelper()
        }
    }

    suspend fun insertList(data: List<CovidDataModel?>, stateModel: List<CovidStateModel>) {
        covidDao?.insertAll(data)
        covidStateDao?.insertAll(stateModel)
    }

    suspend fun delete() {
        covidDao?.let {
            it.deleteAll()
        }
        covidStateDao?.let {
            it.deleteAll()
        }
    }

    suspend fun getAllStats(): List<CovidDataModel>? {
        return covidDao?.getAllStats()
    }

    suspend fun searchByCountry(country: String): List<CovidDataModel>? {
        return covidDao?.searchByCountry("$country%") //country starts with
    }

    suspend fun searchByState(state: String,country: String): List<CovidStateModel>? {
        return covidStateDao?.searchBySate("$state%",country) //state starts with
    }

    suspend fun getListByCountry(country: String): List<CovidStateModel>? {
        return covidStateDao?.searchByCountry(country)
    }

}