package com.floward.flowardtask.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.floward.flowardtask.coreui.theme.Pink40
import com.floward.flowardtask.coreui.theme.Purple40
import com.floward.flowardtask.presentation.model.PostUiModel
import com.floward.flowardtask.presentation.model.UserPostUIModel

@Composable
fun PostScreen(
    userPosts: List<UserPostUIModel>,
    userImageUrl: String?,
) {
    // Flatten the list of UserPostUIModel to get a list of PostUiModel
    val posts: List<PostUiModel> = userPosts.flatMap { userPost ->
        userPost.posts
    }

    Column {
        UserHeader(userImageUrl = userImageUrl ?: "")

        if (userPosts.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(posts) { post ->
                    PostItem(post = post)
                }
            }
        } else {
            Text(
                text = "No posts available",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Composable
fun UserHeader(userImageUrl: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (userImageUrl.isNotBlank()) {
            Image(
                painter = rememberAsyncImagePainter(model = userImageUrl),
                contentDescription = "User Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.78f) // Adjust aspect ratio to control height based on width, or remove it if not needed
                    .clip(RoundedCornerShape(8.dp)), // Optional: adds a rounded corner effect
                contentScale = ContentScale.Crop
            )
        } else {
            // Placeholder for missing image
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .align(Alignment.Center)
            ) {
                Text(
                    text = "No Image",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun PostItem(post: PostUiModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // Adjust the padding around the card
        elevation = CardDefaults.cardElevation(4.dp), // Use CardDefaults.cardElevation
        shape = RoundedCornerShape(8.dp) // Optional: Rounded corners
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Padding inside the card
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.headlineMedium,
                color = Purple40
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.body,
                style = MaterialTheme.typography.headlineLarge,
                color = Pink40
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}