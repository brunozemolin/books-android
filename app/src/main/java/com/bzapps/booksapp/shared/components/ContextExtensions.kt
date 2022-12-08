package com.bzapps.booksapp.shared.components

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.bzapps.booksapp.R

fun Context.closeKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun makeLoading(context: Context): View {
    return LayoutInflater.from(context).inflate(R.layout.view_full_loading, null, false)
}


