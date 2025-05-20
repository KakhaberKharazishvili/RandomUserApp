package com.example.randomuserapp.di

import com.example.randomuserapp.viewmodel.UserDetailViewModel
import com.example.randomuserapp.viewmodel.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::UserListViewModel)

    viewModel { (userId: Int) ->
        UserDetailViewModel(get(), userId)
    }
}
