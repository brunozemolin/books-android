package com.bzapps.booksapp.data.repository.core

import com.bzapps.booksapp.data.model.book.BookResponseDto
import com.bzapps.booksapp.data.model.favorite.FavoriteResponseDto
import com.bzapps.booksapp.data.model.login.LoginResponseDto
import com.bzapps.booksapp.data.model.response.ResponseRequired

interface ICoreRepository {

    suspend fun autoLogin(): ResponseRequired<Boolean>

    suspend fun login(login: String, password: String): ResponseRequired<LoginResponseDto>

    suspend fun getBooks(name: String): ResponseRequired<List<BookResponseDto>>

    suspend fun getFavoriteBooks(): ResponseRequired<List<FavoriteResponseDto>>

    suspend fun setUserId(userId: String): ResponseRequired<Boolean>

    suspend fun getUserId(): ResponseRequired<String>

    suspend fun setFavorite(bookId: String): ResponseRequired<FavoriteResponseDto>

    suspend fun removeFavorite(bookId: String): ResponseRequired<Boolean>

    suspend fun clearUser(): Boolean

    suspend fun includeBook(
        name: String,
        author: String,
        genre: String,
        imageUrl: String
    ): ResponseRequired<Boolean>

}