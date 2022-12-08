package com.bzapps.booksapp.di

import com.bzapps.booksapp.data.local.di.localModule
import com.bzapps.booksapp.data.remote.di.remoteModule
import com.bzapps.booksapp.data.repository.di.repositoryModule
import com.bzapps.booksapp.modules.home.di.homeModule
import com.bzapps.booksapp.modules.launcher.di.launcherModule
import com.bzapps.booksapp.modules.login.di.loginModule
import org.koin.dsl.module

val appModule =
    listOf(
        remoteModule,
        launcherModule,
        loginModule,
        localModule,
        repositoryModule,
        homeModule
    )

