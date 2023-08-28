package com.bera.techconferenceapp

import android.app.Application
import com.bera.techconferenceapp.domain.di.appModule
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}