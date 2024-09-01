package com.floward.flowardtask.presentation

import com.floward.flowardtask.presentation.model.UserPostUIModel


data class UserState(
    val data: List<UserPostUIModel?>? = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false,
    val postCountMap: Map<Int, Int> = emptyMap(),
    val userPostsList: List<UserPostUIModel?>? = emptyList(), // All user posts
    val relatedUserPosts: List<UserPostUIModel?>? = emptyList() // Posts for specific user

)