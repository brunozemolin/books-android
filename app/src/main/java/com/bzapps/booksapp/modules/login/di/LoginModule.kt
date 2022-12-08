package com.bzapps.booksapp.modules.login.di

import com.bzapps.booksapp.modules.login.usecase.LoginUseCase
import com.bzapps.booksapp.modules.login.usecase.SetUserIdPreferenceUseCase
import com.bzapps.booksapp.modules.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory { SetUserIdPreferenceUseCase() }
    factory { LoginUseCase() }
    viewModel { LoginViewModel(get(), get()) }
}
