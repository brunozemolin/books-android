package com.bzapps.booksapp.data.remote.model.favorite

import com.squareup.moshi.Json

data class FavoriteRequestVo(
    @field:Json(name = "bookId")
    val bookId: String,

    @field:Json(name = "userId")
    val userId: String
)
