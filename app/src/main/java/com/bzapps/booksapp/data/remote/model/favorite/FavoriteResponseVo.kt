package com.bzapps.booksapp.data.remote.model.favorite

import com.bzapps.booksapp.data.remote.model.book.BookResponseVo
import com.squareup.moshi.Json

data class FavoriteResponseVo(
    @field:Json(name = "book")
    val book: BookResponseVo,

    @field:Json(name = "userName")
    val userName: String,

    @field:Json(name = "uuid")
    val id: String,
)

