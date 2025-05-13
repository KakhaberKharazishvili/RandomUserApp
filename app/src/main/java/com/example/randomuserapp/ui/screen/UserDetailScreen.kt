package com.example.randomuserapp.ui.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.randomuserapp.R
import com.example.randomuserapp.data.db.AppDatabase
import com.example.randomuserapp.repository.UserRepositoryImpl
import com.example.randomuserapp.viewmodel.UserDetailViewModel
import com.example.randomuserapp.viewmodel.UserDetailViewModelFactory

@Composable
fun UserDetailScreen(userId: String) {
    val context = LocalContext.current
    val viewModel = provideUserDetailViewModel(context, userId.toInt())
    val user by viewModel.user.collectAsState()

    if (user == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = user!!.avatarUrl),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("${user!!.firstName} ${user!!.lastName}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = context.getString(R.string.label_dob, user!!.birthDate))
            Text(text = context.getString(R.string.label_age, user!!.age))
            Text(text = context.getString(R.string.label_address, "${user!!.street}, ${user!!.city}, ${user!!.country}"))
            Text(text = context.getString(R.string.label_phone, user!!.phone))
            Text(text = context.getString(R.string.label_email, user!!.email))
        }
    }
}

@Composable
fun provideUserDetailViewModel(context: Context, userId: Int): UserDetailViewModel {
    val factory = remember(context, userId) {
        val db = AppDatabase.getDatabase(context)
        val repository = UserRepositoryImpl(db.userDao())
        UserDetailViewModelFactory(repository, userId)
    }
    return viewModel(factory = factory)
}
