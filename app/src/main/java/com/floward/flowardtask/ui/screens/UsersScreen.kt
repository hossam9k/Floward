package com.floward.flowardtask.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.floward.flowardtask.R
import com.floward.flowardtask.presentation.UserViewModel
import com.floward.flowardtask.presentation.model.UserPostUIModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Composable
fun UsersScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val userState by userViewModel.userPostDataState.collectAsState()

    if (userState.data?.isNotEmpty()!!)
        LazyColumn {
            items(userState.data!!) { res ->
                if (res != null) {
                    UserList(data = res) {
                        val relatedPosts = userViewModel.getRelatedPostsForUser(res.userId)
                        val userPostsJson = Json.encodeToString(relatedPosts)
                        val userImageUrl = Uri.encode(res.url)
                        navHostController.navigate("post_screen/${Uri.encode(userPostsJson)}/$userImageUrl")
                    }
                }
            }
        }
}

@Composable
fun UserList(data: UserPostUIModel, onGettingClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        onClick = { onGettingClick() }

    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.thumbnailUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .crossfade(true)
                        .transformations(CircleCropTransformation())
                        .build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = data.name,
                    style = typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = data.postCount.toString(),
                    style = typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

            }
        }
    }
}