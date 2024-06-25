package com.example.myapp1.network

import com.example.myapp1.models.Comments
import retrofit2.Call
import retrofit2.http.GET

interface MyAPI {
    @GET("/comments")
    fun getComments(): Call<List<Comments>>
}