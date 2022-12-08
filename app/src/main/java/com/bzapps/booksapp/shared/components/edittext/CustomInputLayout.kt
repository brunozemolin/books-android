package com.bzapps.booksapp.shared.components.edittext

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.bzapps.booksapp.R
import com.google.android.material.textfield.TextInputLayout

class CustomInputLayout(context: Context, attrs: AttributeSet) : TextInputLayout(context, attrs) {

    var type: String? = null

    init {
        loadAttributes(attrs)
    }

    private fun loadAttributes(attributeSet: AttributeSet) {
        val allAttributes: TypedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.CustomInputLayout)
        type = allAttributes.getString(R.styleable.CustomInputLayout_textInputLayoutType)
        allAttributes.recycle()
    }

    fun showError(errorText: String?) {
        isErrorEnabled = true
        error = errorText
    }

    fun hideError() {
        isErrorEnabled = false
        error = null
    }

}