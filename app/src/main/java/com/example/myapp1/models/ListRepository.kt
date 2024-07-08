package com.example.myapp1.models

import com.example.myapp1.network.MyAPI
import com.example.myapp1.network.MyRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class ListRepository(private val api: MyAPI) : MyRepository {
    private val HTTP_BASE_URL = "https://jsonplaceholder.typicode.com"
    private var returnList: List<Comments>? = null


    //###############NOT USED################
   /* @OptIn(DelicateCoroutinesApi::class)
    fun getNetworkResponse(): List<Comments>? {

        GlobalScope.launch(Dispatchers.IO) {
            val comments = api?.getComments()
            if (comments?.isSuccessful == true) {
                returnList = comments.body()!!
            }
        }
        return returnList
    }*/

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
