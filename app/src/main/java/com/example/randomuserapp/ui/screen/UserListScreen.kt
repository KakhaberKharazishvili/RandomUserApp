package com.example.randomuserapp.ui.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.randomuserapp.R
import com.example.randomuserapp.data.db.UserEntity
import com.example.randomuserapp.repository.UserRepositoryProvider
import com.example.randomuserapp.viewmodel.UserListViewModel
import com.example.randomuserapp.viewmodel.UserListViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(onUserClick: (String) -> Unit) {
    val context = LocalContext.current
    val viewModel = provideUserViewModel(context)
    val users by viewModel.userList.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

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
                UserList(users = users, onUserClick = onUserClick)
            }
        }
    }
}

@Composable
fun UserList(
    users: List<UserEntity>,
    onUserClick: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(users, key = { index, user -> "${user.id}_$index" }) { _, user ->
            Text(
                text = "${user.firstName} ${user.lastName}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable { onUserClick(user.id.toString()) }
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun provideUserViewModel(context: Context): UserListViewModel {
    val factory = remember(context) {
        val repository = UserRepositoryProvider.getRepository(context)
        UserListViewModelFactory(repository)
    }
    return viewModel(factory = factory)
}
