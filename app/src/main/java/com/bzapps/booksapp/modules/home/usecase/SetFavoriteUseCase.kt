package com.bzapps.booksapp.modules.home.usecase

import com.bzapps.booksapp.data.repository.core.ICoreRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SetFavoriteUseCase : KoinComponent {
    private val iCoreRepository: ICoreRepository by inject()

    suspend operator fun invoke(
        uuid: String
    ) = iCoreRepository.setFavorite(uuid)
}