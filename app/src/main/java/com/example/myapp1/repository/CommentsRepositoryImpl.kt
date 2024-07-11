package com.example.myapp1.repository

import com.example.myapp1.models.Comments
import com.example.myapp1.models.DataStatus
import com.example.myapp1.network.CommentsAPI
import com.example.myapp1.repository.Utils.Companion.RESULT_SUCCESS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.example.myapp1.repository.Utils.Companion.TIME_DELAY
import kotlinx.coroutines.flow.Flow

class CommentsRepositoryImpl @Inject constructor(
    private val api: CommentsAPI
) : CommentsRepository {

    override suspend fun getCommentsFromAPI() = flow {
        emit(DataStatus.loading())
        //for mocking the progress bar.
        delay(timeMillis = TIME_DELAY)
        val result = api.getComments()
        when (result.code()) {
            RESULT_SUCCESS -> {
                emit(DataStatus.success(result.body()))
            }
        }
    }
        .catch {
            emit(DataStatus.error("Error"))
        }
        .flowOn(Dispatchers.IO)

    override suspend fun getCommentsFromAPIForId(postId: Int) = flow {
        emit(DataStatus.loading())
        //for mocking the progress bar.
        delay(timeMillis = TIME_DELAY)
        val result = api.getPostDetails(postId)
        when (result.code()) {
            RESULT_SUCCESS -> {
                emit(DataStatus.success(result.body()))
            }
        }
    }
        .catch {
            emit(DataStatus.error("Error"))
        }
        .flowOn(Dispatchers.IO)
}
