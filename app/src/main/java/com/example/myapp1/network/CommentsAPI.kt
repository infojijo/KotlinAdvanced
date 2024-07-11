package com.example.myapp1.network

import com.example.myapp1.models.Comments
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentsAPI {
    @GET("/comments")
    suspend fun getComments(): Response<List<Comments>>

    @GET("/comments")
    suspend fun getPostDetails(@Query("postId") postId: Int?): Response<List<Comments>>
}