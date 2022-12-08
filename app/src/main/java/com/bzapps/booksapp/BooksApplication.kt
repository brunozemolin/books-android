package com.bzapps.booksapp

import com.bzapps.booksapp.di.appModule
import com.google.android.play.core.splitcompat.SplitCompatApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BooksApplication : SplitCompatApplication(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        loadDependencies()
    }


    private fun loadDependencies() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@BooksApplication)
            koin.loadModules(appModule)
        }
    }
}