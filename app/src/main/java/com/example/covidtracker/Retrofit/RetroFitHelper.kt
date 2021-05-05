package com.example.covidtracker.Retrofit


import com.example.covidtracker.model.CovidDataModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Thread Safe Approach
class RetroFitHelper {

    companion object {
        private var instance: RetroFitHelper? = null
        private val LOCK = Any()
        private var covidTrackerServiceApi: CovidTrackerServiceApi? = null
        private const val BASE_URL = "https://covid-api.mmediagroup.fr/v1/"

        operator fun invoke() = instance ?: synchronized(LOCK) {
            instance ?: build().also {
                instance = it
            }
        }


        private fun build(): RetroFitHelper {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
            covidTrackerServiceApi = retrofit.create(CovidTrackerServiceApi::class.java)
            return RetroFitHelper()
        }
    }

    fun getAllCovidCountryStats(): Call<Map<String, Map<String, CovidDataModel>>>? {
        return covidTrackerServiceApi?.getCountryTracker("All")
    }
}
