package com.bzapps.booksapp.data.repository.core

import com.bzapps.booksapp.data.local.database.BookPreferences
import com.bzapps.booksapp.data.model.MapperException
import com.bzapps.booksapp.data.model.book.BookResponseDto
import com.bzapps.booksapp.data.model.favorite.FavoriteResponseDto
import com.bzapps.booksapp.data.model.login.LoginResponseDto
import com.bzapps.booksapp.data.model.response.ResponseRemote
import com.bzapps.booksapp.data.model.response.ResponseRequired
import com.bzapps.booksapp.data.remote.serivce.ICoreService
import com.bzapps.booksapp.data.repository.mapper.BookMapper
import com.bzapps.booksapp.data.repository.mapper.FavoriteMapper
import com.bzapps.booksapp.data.repository.mapper.LoginMapper


class CoreRepository(
    private val bookPreferences: BookPreferences,
    private val coreService: ICoreService,
) : ICoreRepository {

    override suspend fun autoLogin(): ResponseRequired<Boolean> {
       return ResponseRequired.Success(bookPreferences.getUserId().isNotEmpty())
    }

    override suspend fun login(login: String, password: String): ResponseRequired<LoginResponseDto> {
        return try {
            return when (val responseRemote = coreService.requestLogin(login, password)) {
                is ResponseRemote.Success -> {
                    ResponseRequired.Success(LoginMapper.map(responseRemote.response))
                }
                is ResponseRemote.Error -> {
                    ResponseRequired.Error(Exception(responseRemote.throwable.message))
                }
            }
        } catch (mapperException: MapperException) {
            ResponseRequired.Error(Exception(mapperException.message))
        }
    }


    override suspend fun getBooks(name: String): ResponseRequired<List<BookResponseDto>> {
        val userId = bookPreferences.getUserId()
        return try {
            return when (val responseRemote = coreService.getBooks(name, userId)) {
                is ResponseRemote.Success -> {
                    ResponseRequired.Success(BookMapper.map(responseRemote.response))
                }
                is ResponseRemote.Error -> {
                    ResponseRequired.Error(Exception(responseRemote.throwable.message))
                }
            }
        } catch (mapperException: MapperException) {
            ResponseRequired.Error(Exception(mapperException.message))
        }
    }


    override suspend fun getFavoriteBooks(): ResponseRequired<List<FavoriteResponseDto>> {
        val userId = bookPreferences.getUserId()
        return try {
            return when (val responseRemote = coreService.getFavoriteBooks(userId)) {
                is ResponseRemote.Success -> {
                    ResponseRequired.Success(FavoriteMapper.map(responseRemote.response))
                }
                is ResponseRemote.Error -> {
                    ResponseRequired.Error(Exception(responseRemote.throwable.message))
                }
            }
        } catch (mapperException: MapperException) {
            ResponseRequired.Error(Exception(mapperException.message))
        }
    }

    override suspend fun setUserId(
        userId: String,
    ): ResponseRequired<Boolean> {
        return try {
            bookPreferences.setUserId(userId)
            ResponseRequired.Success(result = true)
        } catch (exception: Exception) {
            ResponseRequired.Error(exception)
        }
    }

    override suspend fun getUserId(): ResponseRequired<String> {
        return ResponseRequired.Success(bookPreferences.getUserId())
    }

    override suspend fun setFavorite(bookId: String): ResponseRequired<FavoriteResponseDto> {
        val userId = bookPreferences.getUserId()
        return try {
            return when (val responseRemote = coreService.setFavorite(bookId, userId)) {
                is ResponseRemote.Success -> {
                    ResponseRequired.Success(FavoriteMapper.map(responseRemote.response))
                }
                is ResponseRemote.Error -> {
                    ResponseRequired.Error(Exception(responseRemote.throwable.message))
                }
            }
        } catch (mapperException: MapperException) {
            ResponseRequired.Error(Exception(mapperException.message))
        }
    }

    override suspend fun removeFavorite(bookId: String): ResponseRequired<Boolean> {
        val userId = bookPreferences.getUserId()
        return try {
            return when (val responseRemote = coreService.removeFavorite(bookId, userId)) {
                is ResponseRemote.Success -> {
                    ResponseRequired.Success(true)
                }
                is ResponseRemote.Error -> {
                    ResponseRequired.Error(Exception(responseRemote.throwable.message))
                }
            }
        } catch (mapperException: MapperException) {
            ResponseRequired.Error(Exception(mapperException.message))
        }
    }

    override suspend fun clearUser(): Boolean {
        bookPreferences.clearPreferences()
        return true
    }

    override suspend fun includeBook(
        name: String,
        author: String,
        genre: String,
        imageUrl: String
    ): ResponseRequired<Boolean> {
        return try {
            return when (val responseRemote = coreService.includeBook(name, author, genre, imageUrl)) {
                is ResponseRemote.Success -> {
                    ResponseRequired.Success(true)
                }
                is ResponseRemote.Error -> {
                    ResponseRequired.Error(Exception(responseRemote.throwable.message))
                }
            }
        } catch (mapperException: MapperException) {
            ResponseRequired.Error(Exception(mapperException.message))
        }
    }
}