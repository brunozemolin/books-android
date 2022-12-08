package com.bzapps.booksapp.modules.launcher.di

import com.bzapps.booksapp.modules.launcher.usecase.AutoLoginUseCase
import com.bzapps.booksapp.modules.launcher.viewmodel.LauncherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val launcherModule = module {
    factory { AutoLoginUseCase() }
    viewModel { LauncherViewModel(get()) }
}
