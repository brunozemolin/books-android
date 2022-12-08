package com.bzapps.booksapp.shared.extensions

import android.content.Context
import android.view.View
import android.widget.TextView
import com.bzapps.booksapp.R
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(context: Context, message: String, useAlternativeLayout: Boolean = false) {

    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    val snackbarView = snackbar.view
    val textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView

    if (useAlternativeLayout) {
        snackbarView.setBackgroundColor(context.getColor(android.R.color.white))
        textView.setTextColor(context.getColor(R.color.background_red_medium))
    } else {
        snackbarView.setBackgroundColor(context.getColor(R.color.background_red_medium))
        textView.setTextColor(context.getColor(R.color.white))
    }

    snackbar.show()

}