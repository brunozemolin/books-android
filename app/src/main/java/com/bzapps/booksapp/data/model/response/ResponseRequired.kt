package com.bzapps.booksapp.data.model.response

sealed class ResponseRequired<out T> {
    data class Success<out T>(val result: T): ResponseRequired<T>()
    data class Error(val throwable: Exception): ResponseRequired<Nothing>()
}