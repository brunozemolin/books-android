package com.bzapps.booksapp.data.remote.api

import com.bzapps.booksapp.data.remote.model.book.BookIncludeRequestVo
import com.bzapps.booksapp.data.remote.model.book.BookResponseVo
import com.bzapps.booksapp.data.remote.model.favorite.FavoriteRequestVo
import com.bzapps.booksapp.data.remote.model.favorite.FavoriteResponseVo
import com.bzapps.booksapp.data.remote.model.login.LoginRequestVo
import com.bzapps.booksapp.data.remote.model.login.LoginResponseVo
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface IBookAPI {

    @POST("/users/login")
    suspend fun requestLogin(
        @Body loginRequestVo: LoginRequestVo
    ): LoginResponseVo

    @GET("/books")
    suspend fun getBooks(
        @Query ("name") name: String? = null,
        @Query ("userUuid") userId: String
    ): List<BookResponseVo>

    @GET("/favorites")
    suspend fun getFavoriteBooks(
        @Query ("userUuid") userId: String
    ): List<FavoriteResponseVo>

    @POST("/favorites")
    suspend fun setFavorite(
        @Body favoriteRequest: FavoriteRequestVo
    ): FavoriteResponseVo

    @DELETE("/favorites/{userUuid}/{bookUuid}")
    suspend fun removeFavorite(
        @Path("userUuid") userId: String,
        @Path("bookUuid") bookId: String
    )

    @POST("/books")
    suspend fun includeBook(
        @Body bookIncludeRequestVo: BookIncludeRequestVo
    )
}
