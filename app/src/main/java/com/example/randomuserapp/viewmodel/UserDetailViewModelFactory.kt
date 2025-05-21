package com.example.randomuserapp.viewmodel

import dagger.assisted.AssistedFactory

@AssistedFactory
interface UserDetailViewModelFactory {
    fun create(userId: Int): UserDetailViewModel
}
