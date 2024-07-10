package com.example.myapp1.repository

import com.example.myapp1.models.Comments
import com.example.myapp1.models.DataStatus
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {
    suspend fun getCommentsFromAPI(): Flow<DataStatus<List<Comments>>>
}