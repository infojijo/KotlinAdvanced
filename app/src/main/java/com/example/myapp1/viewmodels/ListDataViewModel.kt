package com.example.myapp1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp1.models.Comments
import com.example.myapp1.models.DataStatus
import com.example.myapp1.models.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDataViewModel @Inject constructor(private val myRepository: ListRepository) : ViewModel() {
    private val _flowList = MutableLiveData<DataStatus<List<Comments>>>()
    val flowList: LiveData<DataStatus<List<Comments>>>
        get() = _flowList

    fun getFlowResult() {
        viewModelScope.launch {
            myRepository.getFlowResponse().collect {
                _flowList.value = it
            }
        }
    }
}