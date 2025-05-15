package com.example.randomuserapp.ui.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.randomuserapp.R
import com.example.randomuserapp.data.db.UserEntity
import com.example.randomuserapp.repository.UserRepository
import com.example.randomuserapp.viewmodel.UserDetailViewModel
import com.example.randomuserapp.viewmodel.UserDetailViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(userId: Int) {
    val context = LocalContext.current
    val viewModel = provideUserDetailViewModel(context, userId)
    val user by viewModel.user.collectAsStateWithLifecycle()

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

@Composable
fun provideUserDetailViewModel(context: Context, userId: Int): UserDetailViewModel {
    val factory = remember(context, userId) {
        val repository = UserRepository.getInstance(context)
        UserDetailViewModelFactory(repository, userId)
    }
    return viewModel(factory = factory)
}
