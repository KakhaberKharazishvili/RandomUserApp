package com.example.randomuserapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.randomuserapp.ui.screen.MainScreen
import com.example.randomuserapp.ui.theme.RandomUserAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomUserAppTheme {
                MainScreen()
            }
        }
    }
}
//Комментарий для пуллреквеста. Привет, Олег!