package com.example.randomuserapp.di

import androidx.room.Room
import com.example.randomuserapp.BuildConfig
import com.example.randomuserapp.data.api.RandomUserApiService
import com.example.randomuserapp.data.db.AppDatabase
import com.example.randomuserapp.repository.UserRepository
import com.example.randomuserapp.repository.UserRepositoryImpl
import com.example.randomuserapp.repository.datasource.LocalUserDataSource
import com.example.randomuserapp.repository.datasource.RemoteUserDataSource
import com.example.randomuserapp.repository.datasource.UserDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single {
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single<RandomUserApiService> {
        get<Retrofit>().create(RandomUserApiService::class.java)
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
}
