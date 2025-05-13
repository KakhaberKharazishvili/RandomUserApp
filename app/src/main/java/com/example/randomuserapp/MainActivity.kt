package com.example.randomuserapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.randomuserapp.ui.screen.UserListScreen
import com.example.randomuserapp.ui.screen.UserDetailScreen
import com.example.randomuserapp.ui.theme.RandomUserAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomUserAppTheme {
                RandomUserApp()
            }
        }
    }
}
@Composable
fun RandomUserApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "user_list"
    ) {
        composable("user_list") {
            UserListScreen(onUserClick = { userId ->
                navController.navigate("user_detail/$userId")
            })
        }
        composable(
            route = "user_detail/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            UserDetailScreen(userId = userId)
        }
    }
}