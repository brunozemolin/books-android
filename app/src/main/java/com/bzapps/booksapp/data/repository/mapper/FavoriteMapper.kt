package com.bzapps.booksapp.data.repository.mapper

import com.bzapps.booksapp.data.model.MapperException
import com.bzapps.booksapp.data.model.favorite.FavoriteResponseDto
import com.bzapps.booksapp.data.remote.model.book.BookResponseVo
import com.bzapps.booksapp.data.remote.model.favorite.FavoriteResponseVo

object FavoriteMapper {

    fun map(payload: List<FavoriteResponseVo>) = payload.sortedBy { it.book.name }.map { map(it) }

    fun map(favoriteResponseVo: FavoriteResponseVo): FavoriteResponseDto {
        return try {
            FavoriteResponseDto(
                id = favoriteResponseVo.id,
                book = BookMapper.map(favoriteResponseVo.book),
                userName = favoriteResponseVo.userName
            )
        } catch (ex: NullPointerException) {
            throw MapperException(ex.localizedMessage!!, errorCode = "BOOKS-0003")
        }
    }
}