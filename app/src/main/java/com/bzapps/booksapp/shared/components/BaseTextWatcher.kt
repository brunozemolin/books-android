package com.bzapps.booksapp.shared.components

import android.text.Editable
import android.text.TextWatcher

abstract class BaseTextWatcher: TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) {}

}