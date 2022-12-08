package com.bzapps.booksapp.modules.home.di

import com.bzapps.booksapp.data.local.database.FirebaseInstance
import com.bzapps.booksapp.modules.home.usecase.*
import com.bzapps.booksapp.modules.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory { GetBooksUseCase() }
    factory { SetFavoriteUseCase() }
    factory { DeleteFavoriteUseCase() }
    factory { GetFavoriteBooksUseCase() }
    factory { ClearUserUseCase() }
    factory { IncludeBookUseCase() }
    factory { FirebaseInstance() }
    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get(), get()) }
}
