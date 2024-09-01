package com.floward.flowardtask.domain.repository

import com.floward.flowardtask.common.Result
import com.floward.flowardtask.data.model.Post
import com.floward.flowardtask.data.model.User
import com.floward.flowardtask.data.model.UserPost
import kotlinx.coroutines.flow.Flow


interface Repository {

    suspend fun getUsers(): Flow<Result<List<User>>>

    suspend fun getPosts(): Flow<Result<List<Post>>>

    suspend fun getUsersAndPosts(): Flow<Result<List<UserPost>>>
}