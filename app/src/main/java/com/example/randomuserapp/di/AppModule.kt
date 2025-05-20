package com.example.randomuserapp.di

import androidx.room.Room
import com.example.randomuserapp.data.api.RandomUserApiService
import com.example.randomuserapp.data.api.RetrofitInstance
import com.example.randomuserapp.data.db.AppDatabase
import com.example.randomuserapp.repository.UserRepository
import com.example.randomuserapp.repository.UserRepositoryImpl
import com.example.randomuserapp.repository.datasource.LocalUserDataSource
import com.example.randomuserapp.repository.datasource.RemoteUserDataSource
import com.example.randomuserapp.repository.datasource.UserDataSource
import com.example.randomuserapp.viewmodel.UserDetailViewModel
import com.example.randomuserapp.viewmodel.UserListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single<RandomUserApiService> {
        RetrofitInstance.api
    }

    single {
        Room.databaseBuilder(
            androidContext(), AppDatabase::class.java, "user-db"
        ).build()
    }

    single { get<AppDatabase>().userDao() }

    single<UserDataSource>(named("remote")) {
        RemoteUserDataSource(get())
    }

    single<UserDataSource>(named("local")) {
        LocalUserDataSource(get())
    }

    single<UserRepository> {
        UserRepositoryImpl(
            remoteDataSource = get(named("remote")), localDataSource = get(named("local"))
        )
    }
    viewModel {
        UserListViewModel(get())
    }
    viewModel { (userId: Int) ->
        UserDetailViewModel(get(), userId)
    }

}
