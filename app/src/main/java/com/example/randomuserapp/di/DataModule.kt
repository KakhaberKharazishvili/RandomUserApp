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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideRandomUserApiService(retrofit: Retrofit): RandomUserApiService =
        retrofit.create(RandomUserApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java, "user_database"
    ).build()

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    @RemoteDataSource
    fun provideRemoteDataSource(api: RandomUserApiService): UserDataSource =
        RemoteUserDataSource(api)

    @Provides
    @Singleton
    @LocalDataSource
    fun provideLocalDataSource(dao: UserDao): UserDataSource = LocalUserDataSource(dao)

    @Provides
    @Singleton
    fun provideUserRepository(
        @RemoteDataSource remote: UserDataSource, @LocalDataSource local: UserDataSource
    ): UserRepository = UserRepositoryImpl(remote, local)

}
