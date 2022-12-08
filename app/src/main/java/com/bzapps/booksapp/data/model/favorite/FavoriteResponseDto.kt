package com.bzapps.booksapp.data.model.favorite

import com.bzapps.booksapp.data.model.book.BookResponseDto

data class FavoriteResponseDto(
    val book: BookResponseDto,
    val userName: String,
    val id: String,
)
