package com.example.covidtracker.ViewModel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.covidtracker.Repository
import com.example.covidtracker.model.CovidDataModel
import com.example.covidtracker.model.CovidStateModel
import com.example.covidtracker.util.BaseViewModel
import com.example.covidtracker.util.ToastHelper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryCovidStatsFragmentViewmodel(application: Application) : BaseViewModel(application) {

    var errorMessage = MutableLiveData<Boolean>()
    var progressCircle = MutableLiveData<Boolean>()
    var listOdCountryStats = MutableLiveData<List<CovidDataModel>>()
    var errorApi = MutableLiveData<Boolean>()
    private val refreshInterval = 5 * 60 * 1000 * 1000 * 1000L


    fun loadData() {
        progressCircle.value = true
        errorMessage.value = false
        val lastUpdateTime = Repository.sharedPrefHelper.getLastUpdateTime()
        lastUpdateTime?.let {
            if (lastUpdateTime != 0L && System.nanoTime() - it <= refreshInterval) loadDataFromCache()
            else loadDataFromServer()
        }

    }

    private fun loadDataFromServer() {
        //ToastHelper.smallMessage(getApplication(), "Loading From Api")
        Repository.retroFitHelper.getAllCovidCountryStats()?.let {
            it.enqueue(object : Callback<Map<String, Map<String, CovidDataModel>>> {
                override fun onFailure(
                    call: Call<Map<String, Map<String, CovidDataModel>>>,
                    t: Throwable
                ) {

                    Log.e("TEST", "$t")
                    errorApi.value = true
                }

                @RequiresApi(Build.VERSION_CODES.N)
                override fun onResponse(
                    call: Call<Map<String, Map<String, CovidDataModel>>>,
                    response: Response<Map<String, Map<String, CovidDataModel>>>
                ) {
                    val result = response.body()
                    var listOfDataModel = mutableListOf<CovidDataModel>()
                    var listOfStateModel = mutableListOf<CovidStateModel>()
                    result?.let {
                        it.forEach { country, value ->
                            value.forEach { state, value2 ->
                                if (state != "All") {
                                    if (value2.country.isNullOrBlank()) {
                                        value2.let {
                                            listOfStateModel.add(
                                                CovidStateModel(
                                                    lat = it.lat,
                                                    longitude = it.long,
                                                    confirmed = it.confirmed.toString(),
                                                    recovered = it.recovered,
                                                    deaths = it.deaths,
                                                    updated = it.updated,
                                                    country = country,
                                                    state = state
                                                )
                                            )
                                        }
                                    }
                                } else {
                                    var countryStats = value2
                                    countryStats.country = country
                                    listOfDataModel.add(countryStats)
                                }
                            }

                        }
                    }
                    listOdCountryStats.value = listOfDataModel
                    progressCircle.value = false
                    errorMessage.value = false
                    launch {
                        Repository.roomDatabaseHelper.delete()
                        Repository.roomDatabaseHelper.insertList(listOfDataModel, listOfStateModel)
                        Log.v("INSERT", "DONE INSERT INTO  DB")
                    }
                    Repository.sharedPrefHelper.setLastUpdateTime(System.nanoTime())
                }
            })
        }
    }

    private fun loadDataFromCache() {
        launch {
           // ToastHelper.smallMessage(getApplication(), "Loading From Cache")
            Repository.roomDatabaseHelper.getAllStats()?.let {
                listOdCountryStats.value = it
                progressCircle.value = false
                errorMessage.value = false
            }
        }
    }

    fun searchByCountry(country: String) {
        launch {
            Repository.roomDatabaseHelper.searchByCountry(country)?.let {
                listOdCountryStats.value = it
                progressCircle.value = false
            }
        }
    }
}