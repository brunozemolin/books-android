package com.bzapps.booksapp.data.local.database

import android.content.SharedPreferences

class BasePreferences(
    private val sharedPreferences: SharedPreferences
) {

    fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String {
        return sharedPreferences.getString(key, "").toString()
    }

    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }

}