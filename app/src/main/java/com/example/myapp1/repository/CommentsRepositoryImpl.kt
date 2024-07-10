package com.example.myapp1.repository

import com.example.myapp1.models.DataStatus
import com.example.myapp1.network.CommentsAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val api: CommentsAPI
) : CommentsRepository {

    override suspend fun getCommentsFromAPI() = flow {
        emit(DataStatus.loading())
        //for mocking the progress bar.
        delay(timeMillis = 4000)
        val result = api.getComments()
        when (result.code()) {
            200 -> {
                emit(DataStatus.success(result.body()))
            }
        }
    }
        .catch {
            emit(DataStatus.error("Error"))
        }
        .flowOn(Dispatchers.IO)
}