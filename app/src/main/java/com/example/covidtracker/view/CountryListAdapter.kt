package com.example.covidtracker.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.covidtracker.R
import com.example.covidtracker.databinding.ItemCountryBinding
import com.example.covidtracker.model.CovidDataModel
import com.example.covidtracker.util.ViewClickListener
import kotlinx.android.synthetic.main.item_country.view.*

class CountryListAdapter(var dataSet: List<CovidDataModel>) :
    RecyclerView.Adapter<CountryListAdapter.ViewHolder>(), ViewClickListener {

    fun updateList(data: List<CovidDataModel>) {
        dataSet = data
        notifyDataSetChanged()
    }

    class ViewHolder(val view: ItemCountryBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val itemCountryBinding = DataBindingUtil.inflate<ItemCountryBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_country,
            viewGroup,
            false
        )
        return ViewHolder(itemCountryBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.covidCountry = dataSet[position]
        viewHolder.view.clickListener = this
    }

    override fun getItemCount() = dataSet.size

    override fun onViewClickListener(view: View) {
        val action = CountryCovidStatsFragmentDirections.actionCountryCovidStatsToStatesCovidStats()
        action?.let {
            it.country = view.country.text.toString()
        }
        Navigation.findNavController(view).navigate(action)
    }
}
