package com.floward.flowardtask.data.network

import com.floward.flowardtask.data.model.Post
import com.floward.flowardtask.data.model.User
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}