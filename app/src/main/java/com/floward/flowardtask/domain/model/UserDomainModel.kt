package com.floward.flowardtask.domain.model


data class UserDomainModel(
    val albumId: Int,
    val name: String,
    val thumbnailUrl: String,
    val url: String,
    val userId: Int,
    val posts: List<PostDomainModel>
)

data class PostDomainModel(
    val body: String,
    val id: Int,
    val title: String,
    val postUserId: Int,
    val postCount: Int? = null,
)