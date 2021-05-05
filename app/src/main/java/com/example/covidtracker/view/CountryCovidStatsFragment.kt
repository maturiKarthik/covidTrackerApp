package com.example.covidtracker.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covidtracker.R
import com.example.covidtracker.ViewModel.CountryCovidStatsFragmentViewmodel
import com.example.covidtracker.util.SearchBarHelperUtil
import com.example.covidtracker.util.SearchBarListener
import kotlinx.android.synthetic.main.fragment_country_covid_stats.*

class CountryCovidStatsFragment : Fragment(), SearchBarListener {

    var countryListAdapter = CountryListAdapter(arrayListOf())
    lateinit var countryStatsViewmodel: CountryCovidStatsFragmentViewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_covid_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        countryStatsViewmodel =
            ViewModelProviders.of(this).get(CountryCovidStatsFragmentViewmodel::class.java)
        countryStatsViewmodel.loadData()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryListAdapter
        }

        observer()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        context?.let {
            val searchManager = menu.findItem(R.id.app_bar_search)
            val searchView = SearchBarHelperUtil(this, it)
            searchManager.setActionView(searchView) // Adding custom search bar
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDataFly(query: String) {
        countryStatsViewmodel.searchByCountry(query)
    }

    override fun dataOnSubmit(query: String) {
        countryStatsViewmodel.searchByCountry(query)
    }

    override fun onSearchBarClose() {
        countryStatsViewmodel.loadData()
    }
}