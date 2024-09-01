package com.floward.flowardtask.data.mapper

import com.floward.flowardtask.common.base.Mapper
import com.floward.flowardtask.data.model.UserPost
import com.floward.flowardtask.domain.model.PostDomainModel
import com.floward.flowardtask.domain.model.UserDomainModel
import javax.inject.Inject

class UsersAndPostsToDomainMapper @Inject constructor() :
    Mapper<UserPost, List<UserDomainModel>> {
    override fun mapFrom(from: UserPost): List<UserDomainModel> {
        return from.posts.map { post ->
            UserDomainModel(
                albumId = from.albumId,
                name = from.name,
                thumbnailUrl = from.thumbnailUrl,
                url = from.url,
                userId = from.userId,
                posts = listOf(
                    PostDomainModel(
                        body = post.body,
                        id = post.id,
                        title = post.title,
                        postUserId = post.userId
                    )
                )
            )
        }
    }
}