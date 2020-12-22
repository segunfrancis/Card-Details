package com.segunfrancis.carddetails.presentation

import android.app.Application
import com.segunfrancis.carddetails.presentation.di.useCaseModule
import com.segunfrancis.carddetails.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.scope.BuildConfig
import org.koin.core.context.startKoin
import timber.log.Timber

class BinApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@BinApp)
            modules(useCaseModule, viewModelModule)
        }
    }
}