package com.example.myapp1.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp1.models.Comments
import com.example.myapp1.models.DataStatus
import com.example.myapp1.repository.CommentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsListViewModel @Inject constructor(private val commentsRepository: CommentsRepository) :
    ViewModel() {
    private var showProgress by mutableStateOf(true)
    private val _commentList = MutableLiveData<DataStatus<List<Comments>>>()
    val commentList: LiveData<DataStatus<List<Comments>>>
        get() = _commentList

    fun isProgressShowing(): Boolean = showProgress
    fun hideProgress() {
        showProgress = false
    }

    fun getCommentList(): MutableLiveData<DataStatus<List<Comments>>> = _commentList
    fun showProgress() {
        showProgress = true
    }

    fun getComments() {
        viewModelScope.launch {
            commentsRepository.getCommentsFromAPI().collect {
                _commentList.value = it
            }
        }
    }

    fun getCommentsForId(postId: Int) {
        viewModelScope.launch {
            commentsRepository.getCommentsFromAPIForId(postId).collect {
                _commentList.value = it
            }
        }
    }
}