package com.floward.flowardtask.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)
