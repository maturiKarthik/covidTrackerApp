package com.example.covidtracker.util

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.widget.SearchView


@SuppressLint("ViewConstructor")
class SearchBarHelperUtil(var searchBarListener: SearchBarListener, context: Context) :SearchView(context),
    SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    init {
        //searchView.queryHint = "Search By Country .."
        this.queryHint = "Search By Country .."
        this.setOnQueryTextListener(this)
        this.setOnCloseListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchBarListener.dataOnSubmit(it)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            searchBarListener.onDataFly(it)
        }
        return true
    }

    override fun onClose(): Boolean {
        searchBarListener.onSearchBarClose()
        return false
    }
}

interface SearchBarListener {
    fun onDataFly(query: String) //For every key stroke
    fun dataOnSubmit(query: String) // Data On pressing the submit button
    fun onSearchBarClose() // No value passed since if this is called search bar is closed
}