package com.example.myapp1.models

import com.example.myapp1.network.MyAPI
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

object ListRepository {
    private val HTTP_BASE_URL = "https://jsonplaceholder.typicode.com"
    private var returnList: List<Comments>? = null
    private var api: MyAPI? = null

    init {
        api = Retrofit
            .Builder()
            .baseUrl(HTTP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyAPI::class.java)
    }

    //###############NOT USED################
    @OptIn(DelicateCoroutinesApi::class)
    fun getNetworkResponse(): List<Comments>? {

        GlobalScope.launch(Dispatchers.IO) {
            val comments = api?.getComments()
            if (comments?.isSuccessful == true) {
                returnList = comments.body()!!
            }
        }
        return returnList
    }

    suspend fun getFlowResponse() = flow {
        emit(DataStatus.loading())
        val result = api?.getComments()
        when (result?.code()) {
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
