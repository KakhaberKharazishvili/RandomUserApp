package com.example.randomuserapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.randomuserapp.data.navigation.UserList
import com.example.randomuserapp.data.navigation.UserDetail
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

    NavHost(navController = navController, startDestination = UserList) {
        composable<UserList> {
            UserListScreen(onUserClick = { userId ->
                navController.navigate(UserDetail(userId.toInt()))
            })
        }

        composable<UserDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<UserDetail>()
            UserDetailScreen(userId = args.userId)
        }
    }
}
