package com.floward.flowardtask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.floward.flowardtask.common.doOnFailure
import com.floward.flowardtask.common.doOnLoading
import com.floward.flowardtask.common.doOnSuccess
import com.floward.flowardtask.domain.model.UserDomainModel
import com.floward.flowardtask.domain.usecase.GetUsersAndPostsUseCase
import com.floward.flowardtask.presentation.mapper.DomainToUIModelMapper
import com.floward.flowardtask.presentation.model.UserPostUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersAndPostsUseCase: GetUsersAndPostsUseCase,
    private val domainToUIModelMapper: DomainToUIModelMapper,
) : ViewModel() {
    private val _userPostDataState = MutableStateFlow(UserState())
    val userPostDataState: StateFlow<UserState> get() = _userPostDataState


    init {
        getUsersAndPosts()
    }

    private fun getUsersAndPosts() = viewModelScope.launch {
        getUsersAndPostsUseCase()
            .doOnLoading {
                _userPostDataState.value = UserState(isLoading = true)
            }
            .doOnSuccess { data ->
                val processedUserPosts = data?.let { processUserPosts(it) } ?: emptyList()
                _userPostDataState.value = UserState(data = processedUserPosts)
            }
            .doOnFailure { error ->
                _userPostDataState.value = UserState(error = error.toString())
            }
            .collect()
    }

    private fun processUserPosts(userPostDomainList: List<UserDomainModel>): List<UserPostUIModel> {
        val userMap = createUserMap(userPostDomainList)
        val userPostsWithAggregatedData = mapToUIModels(userMap)
        val postCountMap = calculatePostCount(userPostDomainList)
        return updateUIModelsWithPostCount(userPostsWithAggregatedData, postCountMap)
    }

    private fun createUserMap(userPostDomainList: List<UserDomainModel>): Map<Int, UserDomainModel> {
        return userPostDomainList.groupBy { it.userId }
            .mapValues { entry ->
                val user = entry.value.first() // There will be only one user object per userId
                user.copy(posts = entry.value.flatMap { it.posts })
            }
    }

    private fun mapToUIModels(userMap: Map<Int, UserDomainModel>): List<UserPostUIModel> {
        return userMap.values.map { user ->
            domainToUIModelMapper.mapFrom(user)
        }
    }

    private fun calculatePostCount(userPostDomainList: List<UserDomainModel>): Map<Int, Int> {
        return userPostDomainList
            .flatMap { user -> user.posts.map { user.userId } }
            .groupingBy { it }
            .eachCount()
    }

    private fun updateUIModelsWithPostCount(
        userPostsWithAggregatedData: List<UserPostUIModel>,
        postCountMap: Map<Int, Int>
    ): List<UserPostUIModel> {
        return userPostsWithAggregatedData.map { userPost ->
            userPost.copy(postCount = postCountMap[userPost.userId] ?: 0)
        }
    }

    fun getRelatedPostsForUser(userId: Int): List<UserPostUIModel?>? {
        val userPostsList = _userPostDataState.value.data
        return userPostsList?.filter { it?.userId == userId }
    }
}