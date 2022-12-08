package com.bzapps.booksapp.data.remote.model.book

import com.squareup.moshi.Json

data class BookIncludeRequestVo(

    @field:Json(name = "author")
    val author: String,

    @field:Json(name = "genre")
    val genre: String,

    @field:Json(name = "name")
    val name: String,

    @field:Json(name = "url")
    val url: String

)