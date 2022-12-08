package com.bzapps.booksapp.data.remote.serivce

import com.bzapps.booksapp.data.model.response.ResponseRemote
import com.bzapps.booksapp.data.remote.model.book.BookResponseVo
import com.bzapps.booksapp.data.remote.model.favorite.FavoriteResponseVo
import com.bzapps.booksapp.data.remote.model.login.LoginResponseVo

interface ICoreService {

    suspend fun requestLogin(login: String, password: String): ResponseRemote<LoginResponseVo>

    suspend fun getBooks(name: String, userId: String): ResponseRemote<List<BookResponseVo>>

    suspend fun getFavoriteBooks(userId: String): ResponseRemote<List<FavoriteResponseVo>>

    suspend fun setFavorite(bookId: String, userId: String): ResponseRemote<FavoriteResponseVo>

    suspend fun removeFavorite(bookId: String, userId: String): ResponseRemote<Boolean>

    suspend fun includeBook(
        name: String,
        author: String,
        genre: String,
        url: String
    ): ResponseRemote<Boolean>

}
