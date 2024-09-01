package com.floward.flowardtask.presentation.mapper

import com.floward.flowardtask.domain.model.UserDomainModel
import com.floward.flowardtask.presentation.model.PostUiModel
import com.floward.flowardtask.presentation.model.UserPostUIModel
import javax.inject.Inject

class DomainToUIModelMapper @Inject constructor() {
    fun mapFrom(userDomainModel: UserDomainModel): UserPostUIModel {
        return UserPostUIModel(
            albumId = userDomainModel.albumId,
            name = userDomainModel.name,
            thumbnailUrl = userDomainModel.thumbnailUrl,
            url = userDomainModel.url,
            userId = userDomainModel.userId,
            posts = userDomainModel.posts.map { post ->
                PostUiModel(
                    body = post.body,
                    id = post.id,
                    title = post.title,
                    postUserId = post.postUserId,
                    postCount = post.postCount
                )
            }
        )
    }
}