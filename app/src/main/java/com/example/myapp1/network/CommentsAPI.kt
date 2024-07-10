package com.example.myapp1.network

import com.example.myapp1.models.Comments
import retrofit2.Response
import retrofit2.http.GET

interface CommentsAPI {
    @GET("/comments")
    suspend fun getComments(): Response<List<Comments>>
}