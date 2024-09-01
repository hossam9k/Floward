package com.floward.flowardtask.data.repository

import com.floward.flowardtask.common.Result
import com.floward.flowardtask.common.base.BaseRepository
import com.floward.flowardtask.data.model.Post
import com.floward.flowardtask.data.model.User
import com.floward.flowardtask.data.model.UserPost
import com.floward.flowardtask.data.network.ApiService
import com.floward.flowardtask.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : BaseRepository(), Repository {
    override suspend fun getUsers(): Flow<Result<List<User>>> = safeApiCall {
        apiService.getUsers()

    }

    override suspend fun getPosts(): Flow<Result<List<Post>>> = safeApiCall {
        apiService.getPosts()
    }

    override suspend fun getUsersAndPosts(): Flow<Result<List<UserPost>>> =
        combine(getUsers(), getPosts()) { usersResult, postsResult ->
            when {
                usersResult is Result.Success && postsResult is Result.Success -> {
                    val users = usersResult.data
                    val posts = postsResult.data

                    // Group posts by userId
                    val postsByUserId = posts.groupBy { it.userId }

                    // Combine users and their respective posts
                    val combinedList = users.map { user ->
                        UserPost(
                            albumId = user.albumId,
                            name = user.name,
                            thumbnailUrl = user.thumbnailUrl,
                            url = user.url,
                            userId = user.userId,
                            posts = postsByUserId[user.userId]
                                ?: emptyList() // Assign posts or an empty list
                        )
                    }

                    Result.Success(combinedList)
                }

                usersResult is Result.Failure -> Result.Failure(usersResult.msg)
                postsResult is Result.Failure -> Result.Failure(postsResult.msg)
                else -> Result.Failure(IOException("Unknown error occurred"))
            }
        }.catch { e ->
            emit(Result.Failure(e))
        }

}