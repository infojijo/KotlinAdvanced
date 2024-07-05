package com.example.myapp1.network

import com.example.myapp1.models.Comments
import com.example.myapp1.models.DataStatus
import kotlinx.coroutines.flow.Flow

interface MyRepository {
    suspend fun getFlowResponse(): Flow<DataStatus<List<Comments>>>
}