package com.example.covidtracker.ViewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.covidtracker.Repository
import com.example.covidtracker.model.CovidStateModel
import com.example.covidtracker.util.BaseViewModel
import kotlinx.coroutines.launch

class StatesCovidStatsFragmentViewModel(application: Application) : BaseViewModel(application) {

    var listOfStateData = MutableLiveData<List<CovidStateModel>>()


    fun onLoadData(country: String) {
        launch {
            Repository.roomDatabaseHelper.getListByCountry(country)?.let {
                listOfStateData.value = it
            }
        }
    }

    fun onLoadDataByState(state:String,country: String){
        launch {
            Repository.roomDatabaseHelper.searchByState(state,country)?.let {
               listOfStateData.value = it
            }
        }
    }
}