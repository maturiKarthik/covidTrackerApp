package com.example.covidtracker.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.covidtracker.R
import com.example.covidtracker.databinding.ItemStateBinding
import com.example.covidtracker.model.CovidStateModel

class StateListAdapter(var dataSet: List<CovidStateModel>) :
    RecyclerView.Adapter<StateListAdapter.ViewHolder>() {

    fun onUpdateData(data: List<CovidStateModel>) {
        this.dataSet = data
        notifyDataSetChanged()
    }

    class ViewHolder(val view: ItemStateBinding) : RecyclerView.ViewHolder(view.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val itemStateBinding = DataBindingUtil.inflate<ItemStateBinding>(
            LayoutInflater.from(viewGroup.context),
            R.layout.item_state,
            viewGroup,
            false
        )
        return ViewHolder(itemStateBinding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.view.covidSate = dataSet[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
