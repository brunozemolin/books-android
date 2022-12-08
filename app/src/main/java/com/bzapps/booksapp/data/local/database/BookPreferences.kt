package com.bzapps.booksapp.data.local.database


class BookPreferences(
    private val basePreferences: BasePreferences,
) {

    companion object {
        const val KEY_USER_ID: String = "KEY_USER_ID"
    }

    fun getUserId(): String {
        return basePreferences.getString(KEY_USER_ID)
    }

    fun setUserId(id: String) {
        basePreferences.setString(KEY_USER_ID, id)
    }

    fun clearPreferences() {
        basePreferences.clearPreferences()
    }
}