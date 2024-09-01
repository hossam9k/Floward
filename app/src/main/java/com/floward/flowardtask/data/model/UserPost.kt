package com.floward.flowardtask.data.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UserPost(
    val albumId: Int,
    val name: String,
    val thumbnailUrl: String,
    val url: String,
    val userId: Int,
    val posts: List<Post>,
)