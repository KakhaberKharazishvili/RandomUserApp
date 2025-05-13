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
import com.example.randomuserapp.data.navigation.Screen
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
        startDestination = Screen.UserList.route
    ) {
        composable(route = Screen.UserList.route) {
            UserListScreen(onUserClick = { userId ->
                navController.navigate(route = Screen.UserDetail.createRoute(userId.toInt()))
            })
        }
        composable(
            route = Screen.UserDetail.route,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
            UserDetailScreen(userId = userId)
        }
    }
}