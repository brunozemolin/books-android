package com.bzapps.booksapp.shared.components.edittext

internal interface IValidationType {
    companion object {
        const val NO_VALIDATION = 0
        const val VALIDATION_TYPE_TEXT = 1
        const val VALIDATION_TYPE_PASSWORD = 2
    }
}