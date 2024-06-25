package com.example.myapp1.viewmodels

import androidx.lifecycle.ViewModel

class ListDataViewModel : ViewModel() {
    private var myList = mutableListOf("Test", "here", "data", "values")

    fun getMyList(): List<String> = myList
}