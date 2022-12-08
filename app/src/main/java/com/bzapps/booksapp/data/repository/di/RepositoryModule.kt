package com.bzapps.booksapp.data.repository.di

import com.bzapps.booksapp.data.repository.core.CoreRepository
import com.bzapps.booksapp.data.repository.core.ICoreRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<ICoreRepository> {
        CoreRepository(
            get(),
            get()
        )
    }
}