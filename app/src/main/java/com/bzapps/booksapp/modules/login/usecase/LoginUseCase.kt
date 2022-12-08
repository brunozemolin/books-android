package com.bzapps.booksapp.modules.login.usecase

import com.bzapps.booksapp.data.repository.core.ICoreRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginUseCase : KoinComponent {
    private val iCoreRepository: ICoreRepository by inject()

    suspend operator fun invoke(
        cpf: String,
        password: String,
    ) = iCoreRepository.login(cpf, password)
}