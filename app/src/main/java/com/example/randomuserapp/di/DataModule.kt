package com.example.randomuserapp.di

import android.content.Context
import androidx.room.Room
import com.example.randomuserapp.BuildConfig
import com.example.randomuserapp.data.api.RandomUserApiService
import com.example.randomuserapp.data.db.AppDatabase
import com.example.randomuserapp.data.db.UserDao
import com.example.randomuserapp.di.qualifier.LocalDataSource
import com.example.randomuserapp.di.qualifier.RemoteDataSource
import com.example.randomuserapp.repository.UserRepository
import com.example.randomuserapp.repository.UserRepositoryImpl
import com.example.randomuserapp.repository.datasource.LocalUserDataSource
import com.example.randomuserapp.repository.datasource.RemoteUserDataSource
import com.example.randomuserapp.repository.datasource.UserDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    @RemoteDataSource
    abstract fun bindRemoteUserDataSource(
        impl: RemoteUserDataSource
    ): UserDataSource

    @Binds
    @Singleton
    @LocalDataSource
    abstract fun bindLocalUserDataSource(
        impl: LocalUserDataSource
    ): UserDataSource

    companion object {

        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        @Provides
        @Singleton
        fun provideRandomUserApiService(
            retrofit: Retrofit
        ): RandomUserApiService = retrofit.create(RandomUserApiService::class.java)

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): AppDatabase = Room.databaseBuilder(
            context, AppDatabase::class.java, "user_database"
        ).build()

        @Provides
        fun provideUserDao(db: AppDatabase): UserDao = db.userDao()
    }
}
