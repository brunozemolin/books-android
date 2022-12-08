package com.bzapps.booksapp.data.local.di

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.bzapps.booksapp.data.local.database.BasePreferences
import com.bzapps.booksapp.data.local.database.BookPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {

    fun providePreferences(application: Application): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "book-preferences",
            "Etn^er95aj+r6u+5",
            application,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    single { providePreferences(androidApplication()) }
    factory { BookPreferences(get()) }
    factory { BasePreferences(get()) }
}