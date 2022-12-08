package com.bzapps.booksapp.data.remote.model.login

import com.squareup.moshi.Json

data class LoginRequestVo(
    @field:Json(name = "login")
    val login: String,

    @field:Json(name = "password")
    val password: String
)
