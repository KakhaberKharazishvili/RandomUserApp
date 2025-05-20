package com.example.randomuserapp

import android.app.Application
import com.example.randomuserapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RandomUserApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RandomUserApplication)
            modules(appModule)
        }
    }
}