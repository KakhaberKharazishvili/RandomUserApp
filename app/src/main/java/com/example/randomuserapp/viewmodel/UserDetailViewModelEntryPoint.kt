package com.example.randomuserapp.viewmodel

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface UserDetailViewModelEntryPoint {
    fun userDetailViewModelFactory(): UserDetailViewModelFactory
}