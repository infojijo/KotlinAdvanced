package com.example.myapp1.models

import com.example.myapp1.network.MyAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ListRepository {
    private val HTTP_BASE_URL = "https://jsonplaceholder.typicode.com"
    private var returnList: List<Comments>? = null
    fun getNetworkResponse(): List<Comments>? {
        val api = Retrofit
            .Builder()
            .baseUrl(HTTP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyAPI::class.java)
        /*api.getComments().enqueue(object : Callback<List<Comments>> {
            override fun onResponse(
                call: Call<List<Comments>>,
                response: Response<List<Comments>>
            ) {
            }
            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
            }
        })*/
        GlobalScope.launch(Dispatchers.IO) {
            val comments = api.getComments()
            if (comments.isSuccessful) {
                returnList = comments.body()!!
            }
        }
        return returnList
    }
}
