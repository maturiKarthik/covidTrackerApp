package com.example.covidtracker.view

import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_country_covid_stats.*


fun CountryCovidStatsFragment.observer() {
    countryStatsViewmodel.listOdCountryStats.observe(viewLifecycleOwner, Observer {
        it?.let {
            countryListAdapter.updateList(it)
        }
    })

    countryStatsViewmodel.errorMessage.observe(viewLifecycleOwner, Observer {
        if (it) progressBar.visibility = View.VISIBLE else progressBar.visibility = View.GONE
    })

    countryStatsViewmodel.errorMessage.observe(viewLifecycleOwner, Observer {
        if (it) error_msg.visibility = View.VISIBLE else error_msg.visibility = View.GONE
    })
    // Error Api
    countryStatsViewmodel.errorApi.observe(viewLifecycleOwner, Observer {
        it?.let {
            if (it) {
                error_msg.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    })
}

