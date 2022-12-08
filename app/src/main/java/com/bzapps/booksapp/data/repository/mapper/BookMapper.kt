package com.bzapps.booksapp.data.repository.mapper

import com.bzapps.booksapp.data.model.MapperException
import com.bzapps.booksapp.data.model.book.BookResponseDto
import com.bzapps.booksapp.data.remote.model.book.BookResponseVo

object BookMapper {

    fun map(payload: List<BookResponseVo>) = payload.sortedBy { it.name }.map { map(it) }

    fun map(bookResponseVo: BookResponseVo): BookResponseDto {
        return try {
            BookResponseDto(
                uuid = bookResponseVo.id,
                name = bookResponseVo.name,
                url = bookResponseVo.url,
                author = bookResponseVo.author,
                genre = bookResponseVo.genre,
                isFavorite = bookResponseVo.isFavorite,
            )
        } catch (ex: NullPointerException) {
            throw MapperException(ex.localizedMessage!!, errorCode = "BOOKS-0002")
        }
    }
}