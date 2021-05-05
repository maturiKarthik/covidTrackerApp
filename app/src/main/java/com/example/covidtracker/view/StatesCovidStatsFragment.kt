package com.example.covidtracker.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covidtracker.R
import com.example.covidtracker.ViewModel.StatesCovidStatsFragmentViewModel
import com.example.covidtracker.util.SearchBarHelperUtil
import com.example.covidtracker.util.SearchBarListener
import kotlinx.android.synthetic.main.fragment_states_covid_stats.*

class StatesCovidStatsFragment : Fragment(), SearchBarListener {

    private var stateListAdapter = StateListAdapter(arrayListOf())
    private lateinit var stateCovidStatsFragmentViewmodel: StatesCovidStatsFragmentViewModel
    private var country = "empty"
    private lateinit var searchView: SearchBarHelperUtil


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_states_covid_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        recyclerViewState.apply {
            adapter = stateListAdapter
            layoutManager = LinearLayoutManager(context)
        }
        arguments?.let {
            country = it.getString("country").toString()
        }

        stateCovidStatsFragmentViewmodel =
            ViewModelProviders.of(this).get(StatesCovidStatsFragmentViewModel::class.java)
        stateCovidStatsFragmentViewmodel.onLoadData(country)
        observer()
    }

    private fun observer() {
        stateCovidStatsFragmentViewmodel.listOfStateData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                progressBar.visibility = View.GONE
                error_msg.text = "Sorry Couldn't find Data ..!"
            } else {
                it?.let {
                    stateListAdapter.onUpdateData(it)
                }
                progressBar.visibility = View.GONE
                error_msg.visibility = View.GONE

            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        searchView.setQuery("", false)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.state_menu, menu)
        context?.let {
            searchView = SearchBarHelperUtil(this, it)

            val searchManager = menu.findItem(R.id.app_bar_search_state)
            searchManager.setActionView(searchView)
        }
        // Adding custom search bar
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onDataFly(query: String) {
        stateCovidStatsFragmentViewmodel.onLoadDataByState(query, country = country)
    }

    override fun dataOnSubmit(query: String) {
        stateCovidStatsFragmentViewmodel.onLoadDataByState(query, country = country)
    }

    override fun onSearchBarClose() {
        stateCovidStatsFragmentViewmodel.onLoadData(country) // Load by country
    }
}