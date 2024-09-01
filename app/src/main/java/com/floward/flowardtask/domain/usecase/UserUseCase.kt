package com.floward.flowardtask.domain.usecase

import com.floward.flowardtask.common.Result
import com.floward.flowardtask.common.map
import com.floward.flowardtask.data.mapper.UsersAndPostsToDomainMapper
import com.floward.flowardtask.domain.model.UserDomainModel
import com.floward.flowardtask.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUsersAndPostsUseCase @Inject constructor(
    private val repository: Repository,
    private val usersAndPostsToDomainMapper: UsersAndPostsToDomainMapper
) {
    suspend operator fun invoke(): Flow<Result<List<UserDomainModel>?>> {
        return repository.getUsersAndPosts().map { result ->
            result.map { usersAndPostsList ->
                // Flatten the list of lists into a single list of UserPostDomain
                usersAndPostsList.flatMap { usersAndPosts ->
                    usersAndPostsToDomainMapper.mapFrom(usersAndPosts)
                }
            }
        }
    }
}