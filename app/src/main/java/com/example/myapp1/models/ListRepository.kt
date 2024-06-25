package com.example.myapp1.models

import com.example.myapp1.network.MyAPI
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListRepository {

    private val HTTP_BASE_URL = "https://jsonplaceholder.typicode.com"

    fun getNetworkResponse() {
        val api = Retrofit
            .Builder()
            .baseUrl(HTTP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyAPI::class.java)

        api.getComments().enqueue(object : Callback<List<Comments>> {
            override fun onResponse(
                call: Call<List<Comments>>,
                response: Response<List<Comments>>
            ) {

            }
            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {

            }
        })
    }}
