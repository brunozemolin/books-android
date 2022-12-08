package com.bzapps.booksapp.data.model

class MapperException(
    override val message: String,
    val errorCode: String,
) : NullPointerException()