package com.bzapps.booksapp.data.model.response

sealed class ResponseRemote<out T> {
    data class Success<out T>(val response: T): ResponseRemote<T>()

    sealed class Error(open val throwable: Throwable) : ResponseRemote<Nothing>(){
        data class Exception(override val throwable: Throwable): Error(throwable)
    }
}
