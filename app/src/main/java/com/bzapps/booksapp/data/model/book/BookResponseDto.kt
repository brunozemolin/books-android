package com.bzapps.booksapp.data.model.book

data class BookResponseDto(
    var uuid: String? = null,
    var name: String? = "",
    var url: String? = "",
    var author: String? = "",
    var genre: String? = "",
    var isFavorite: Boolean = false
)
