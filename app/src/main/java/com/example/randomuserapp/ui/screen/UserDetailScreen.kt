package com.example.randomuserapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.randomuserapp.R
import com.example.randomuserapp.data.db.UserEntity
import com.example.randomuserapp.viewmodel.UserDetailViewModel
import androidx.hilt.navigation.compose.hiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(userId: Int) {
    val viewModel: UserDetailViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val user = state.user

    LaunchedEffect(userId) {
        viewModel.loadUser(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.details_title)) })
        }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding), contentAlignment = Alignment.Center
        ) {
            Content(user)
        }
    }
}

@Composable
fun Content(user: UserEntity?) {
    if (user == null) {
        CircularProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = rememberAsyncImagePainter(model = user.avatarUrl),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "${user.firstName} ${user.lastName}", fontSize = 20.sp, fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(R.string.label_dob, user.birthDate))
            Text(text = stringResource(R.string.label_age, user.age))
            Text(
                text = stringResource(
                    R.string.label_address, "${user.street}, ${user.city}, ${user.country}"
                )
            )
            Text(text = stringResource(R.string.label_phone, user.phone))
            Text(text = stringResource(R.string.label_email, user.email))
        }
    }
}
