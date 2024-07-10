package com.example.myapp1.models

import android.app.Application
import com.example.myapp1.network.MyAPI
import com.example.myapp1.network.MyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ListRepository(private val api: MyAPI, app: Application) : MyRepository {
    override suspend fun getFlowResponse() = flow {
        emit(DataStatus.loading())
        //for mocking the progress bar.
        delay(4000)
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
