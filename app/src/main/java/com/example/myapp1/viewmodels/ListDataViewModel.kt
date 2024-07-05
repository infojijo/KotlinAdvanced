package com.example.myapp1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp1.models.Comments
import com.example.myapp1.models.DataStatus
import com.example.myapp1.models.ListRepository
import com.example.myapp1.network.MyAPI
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListDataViewModel : ViewModel() {
    private var myList = mutableListOf<String>()
    private val _flowList = MutableLiveData<DataStatus<List<Comments>>>()
    private var api: MyAPI
    private val HTTP_BASE_URL = "https://jsonplaceholder.typicode.com"
    val flowList: LiveData<DataStatus<List<Comments>>>
        get() = _flowList

    init {
        for (i in 1..50) {
            myList.add("Test Data->$i")
        }

        api = Retrofit
            .Builder()
            .baseUrl(HTTP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyAPI::class.java)
    }

    //fun getMyList(): List<Comments>? = ListRepository.getNetworkResponse()

    fun getFlowResult() {
        viewModelScope.launch {
            ListRepository(api = api).getFlowResponse().collect {
                _flowList.value = it
            }
        }
    }
}