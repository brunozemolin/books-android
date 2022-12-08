package com.bzapps.booksapp.data.remote.serivce

import com.bzapps.booksapp.data.model.response.ResponseRemote
import com.bzapps.booksapp.data.remote.api.IBookAPI
import com.bzapps.booksapp.data.remote.model.book.BookIncludeRequestVo
import com.bzapps.booksapp.data.remote.model.book.BookResponseVo
import com.bzapps.booksapp.data.remote.model.favorite.FavoriteRequestVo
import com.bzapps.booksapp.data.remote.model.favorite.FavoriteResponseVo
import com.bzapps.booksapp.data.remote.model.login.LoginRequestVo
import com.bzapps.booksapp.data.remote.model.login.LoginResponseVo

class CoreService(
    private val bookApi: IBookAPI,
) : ICoreService {

    override suspend fun requestLogin(
        login: String,
        password: String
    ): ResponseRemote<LoginResponseVo> {
        return try {
            val payload = bookApi.requestLogin(LoginRequestVo(login, password))
            ResponseRemote.Success(response = payload)
        } catch (throwable: Throwable) {
            ResponseRemote.Error.Exception(throwable)
        }
    }

    override suspend fun getBooks(
        name: String,
        userId: String
    ): ResponseRemote<List<BookResponseVo>> {
        return try {
            val payload = bookApi.getBooks(name = name.ifEmpty { null }, userId = userId)
            ResponseRemote.Success(response = payload)
        } catch (throwable: Throwable) {
            ResponseRemote.Error.Exception(throwable)
        }
    }

    override suspend fun getFavoriteBooks(userId: String): ResponseRemote<List<FavoriteResponseVo>> {
        return try {
            val payload = bookApi.getFavoriteBooks(userId = userId)
            ResponseRemote.Success(response = payload)
        } catch (throwable: Throwable) {
            ResponseRemote.Error.Exception(throwable)
        }
    }

    override suspend fun setFavorite(
        bookId: String,
        userId: String
    ): ResponseRemote<FavoriteResponseVo> {
        return try {
            val payload = bookApi.setFavorite(FavoriteRequestVo(bookId = bookId, userId = userId))
            ResponseRemote.Success(response = payload)
        } catch (throwable: Throwable) {
            ResponseRemote.Error.Exception(throwable)
        }
    }

    override suspend fun removeFavorite(bookId: String, userId: String): ResponseRemote<Boolean> {
        return try {
            bookApi.removeFavorite(userId = userId, bookId = bookId)
            ResponseRemote.Success(true)
        } catch (throwable: Throwable) {
            ResponseRemote.Error.Exception(throwable)
        }
    }

    override suspend fun includeBook(
        name: String,
        author: String,
        genre: String,
        url: String
    ): ResponseRemote<Boolean> {
        return try {
            bookApi.includeBook(
                BookIncludeRequestVo(
                    author = author,
                    name = name,
                    genre = genre,
                    url = url
                )
            )
            ResponseRemote.Success(true)
        } catch (throwable: Throwable) {
            ResponseRemote.Error.Exception(throwable)
        }
    }
}
