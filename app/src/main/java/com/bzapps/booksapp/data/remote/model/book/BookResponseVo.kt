package com.bzapps.booksapp.data.remote.model.book

import com.squareup.moshi.Json

data class BookResponseVo(
    @field:Json(name = "uuid")
    val id: String,

    @field:Json(name = "name")
    var name: String,

    @field:Json(name = "url")
    var url: String? = "",

    @field:Json(name = "author")
    var author: String? = "",

    @field:Json(name = "genre")
    var genre: String? = "",

    @field:Json(name = "isFavorite")
    var isFavorite: Boolean = false
)

