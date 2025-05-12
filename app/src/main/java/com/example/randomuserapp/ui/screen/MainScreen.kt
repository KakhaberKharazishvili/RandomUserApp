package com.example.randomuserapp.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.randomuserapp.R
import com.example.randomuserapp.data.db.AppDatabase
import com.example.randomuserapp.data.db.UserEntity
import com.example.randomuserapp.repository.UserRepository
import com.example.randomuserapp.viewmodel.UserViewModel
import com.example.randomuserapp.viewmodel.UserViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val viewModel = provideUserViewModel(context)
    val users by viewModel.userList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.app_title)) })
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                UserList(users)
            }
        }
    }
}

@Composable
fun UserList(users: List<UserEntity>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(users, key = {it.email}) { user ->
            Text(
                text = "${user.firstName} ${user.lastName}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun provideUserViewModel(context: Context): UserViewModel {
    val factory = remember(context) {
        val db = AppDatabase.getDatabase(context)
        val repository = UserRepository(db.userDao())
        UserViewModelFactory(repository)
    }
    return viewModel(factory = factory)
}
