package com.floward.flowardtask.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.floward.flowardtask.presentation.model.UserPostUIModel
import com.floward.flowardtask.ui.screens.PostScreen
import com.floward.flowardtask.ui.screens.UsersScreen
import kotlinx.serialization.json.Json

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "user_list") {
        composable("user_list") {
            UsersScreen(navHostController = navController)
        }
        composable(
            "post_screen/{userPostsJson}/{userImageUrl}",
            arguments = listOf(
                navArgument("userPostsJson") { type = NavType.StringType },
                navArgument("userImageUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userPostsJson = backStackEntry.arguments?.getString("userPostsJson")
            val userImageUrl = backStackEntry.arguments?.getString("userImageUrl")

            val userPosts = userPostsJson?.let { Json.decodeFromString<List<UserPostUIModel>>(it) }

            userPosts?.let {
                PostScreen(
                    userPosts = it,
                    userImageUrl = userImageUrl,
                )
            }
        }

    }
}