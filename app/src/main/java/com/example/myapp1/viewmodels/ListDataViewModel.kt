package com.example.myapp1.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myapp1.models.Comments
import com.example.myapp1.models.ListRepository

class ListDataViewModel : ViewModel() {
    private var myList = mutableListOf<String>()

    init {
        for (i in 1..50) {
            myList.add("Test Data->$i")
        }
    }

    fun getMyList(): List<Comments>? = ListRepository.getNetworkResponse()
}