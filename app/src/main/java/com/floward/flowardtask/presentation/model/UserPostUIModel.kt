package com.floward.flowardtask.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPostUIModel(
    val albumId: Int,
    val name: String,
    val thumbnailUrl: String,
    val url: String,
    val userId: Int,
    val posts: List<PostUiModel>,
    val postCount: Int? = null,
)

@Serializable
data class PostUiModel(
    val body: String,
    val id: Int,
    val title: String,
    val postUserId: Int,
    val postCount: Int? = null,
)