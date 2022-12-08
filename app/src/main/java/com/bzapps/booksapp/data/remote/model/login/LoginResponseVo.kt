package com.bzapps.booksapp.data.remote.model.login

import com.squareup.moshi.Json

data class LoginResponseVo(
    @field:Json(name = "uuid")
    val id: String,

    @field:Json(name = "name")
    val name: String
)

