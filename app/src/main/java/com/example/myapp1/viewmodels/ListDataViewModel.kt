package com.example.myapp1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp1.models.Comments
import com.example.myapp1.models.ListRepository
import kotlinx.coroutines.launch

class ListDataViewModel : ViewModel() {
    private var myList = mutableListOf<String>()
    private val _flowList = MutableLiveData<List<Comments>>()
    val flowList: LiveData<List<Comments>>
        get() = _flowList

    init {
        for (i in 1..50) {
            myList.add("Test Data->$i")
        }
    }

    fun getMyList(): List<Comments>? = ListRepository.getNetworkResponse()

    fun getFlowResult() {
        viewModelScope.launch {
            ListRepository.getFlowResponse().collect {
                _flowList.value = it
            }
        }
    }
}