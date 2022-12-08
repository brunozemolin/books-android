package com.bzapps.booksapp.data.repository.mapper

import com.bzapps.booksapp.data.model.MapperException
import com.bzapps.booksapp.data.model.login.LoginResponseDto
import com.bzapps.booksapp.data.model.response.ResponseRequired
import com.bzapps.booksapp.data.remote.model.login.LoginResponseVo

object LoginMapper {

    const val ERROR_MAP_LOGIN = "Falha ao realizar o login. Verifique suas credenciais."

    fun map(loginResponseVo: LoginResponseVo): LoginResponseDto {
        return try {
            LoginResponseDto(userId = loginResponseVo.id)
        } catch (ex: NullPointerException) {
            throw MapperException(ex.localizedMessage!!, errorCode = "BOOKS-0001")
        }
    }
}