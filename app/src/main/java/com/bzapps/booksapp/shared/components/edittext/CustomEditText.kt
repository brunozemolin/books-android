package com.bzapps.booksapp.shared.components.edittext

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import com.bzapps.booksapp.R
import com.bzapps.booksapp.shared.components.BaseTextWatcher
import com.bzapps.booksapp.shared.components.form.FormConstraintLayout
import com.bzapps.booksapp.shared.components.form.IFormItem
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.roundToInt


class CustomEditText(context: Context, attrs: AttributeSet) : TextInputEditText(context, attrs),
    IFormItem {

    private var validationType = 0
    private var maskWatcher: TextWatcher? = null
    private var valid = false
    private var required = false
    private var errorMessage: String? = null
    private var validationMessage: String? = null
    private var form: FormConstraintLayout? = null

    init {
        initialize(attrs)
    }

    private fun initialize(attributeSet: AttributeSet) {
        val scale = resources.displayMetrics.density
        setPadding((24 * scale + 0.5f).roundToInt(), 0, 0, 0)
        loadAttributes(attributeSet)
        setupMask()
        setupTextWatcher()
    }

    private fun loadAttributes(attributeSet: AttributeSet) {
        val allAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CustomEdittext)
        validationType = allAttributes.getInteger(
            R.styleable.CustomEdittext_fieldValidationType,
            IValidationType.NO_VALIDATION
        )

        validationMessage =
            allAttributes.getString(R.styleable.CustomEdittext_field_validationMessage)

        required = allAttributes.getBoolean(R.styleable.CustomEdittext_field_required, false)
        allAttributes.recycle()
    }


    private fun setupMask() {
        maskWatcher = when (validationType) {
            IValidationType.VALIDATION_TYPE_TEXT -> builderLoginWatcher()
            IValidationType.VALIDATION_TYPE_PASSWORD -> builderPasswordWatcher()
            else -> null
        }
    }

    private fun builderPasswordWatcher(): TextWatcher {
        return object : BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                handleValidation(s.toString())
            }
        }
    }

    private fun builderLoginWatcher(): TextWatcher {
        return object : BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                handleValidation(s.toString())
            }
        }
    }

    private fun setupTextWatcher() {
        addTextChangedListener(object : BaseTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                maskWatcher?.onTextChanged(s, start, before, count)
            }
        })
    }

    private fun handleValidation(text: String) {
        errorMessage = validationMessage
        when (validationType) {
            IValidationType.VALIDATION_TYPE_TEXT,
            IValidationType.VALIDATION_TYPE_PASSWORD -> valid = text.isNotEmpty()
        }
        setValidation()
    }

    private fun setValidation() {
        form?.updateValidation()

        if (!valid && !errorMessage.isNullOrEmpty()) {
            setErrorMessage(errorMessage)
        } else {
            clearError()
        }
    }

    fun setErrorMessage(errorMessage: String?) {
        val inputLayout = parent.parent as CustomInputLayout
        inputLayout.showError(errorMessage)
    }

    private fun clearError() {
        val inputLayout = parent.parent as CustomInputLayout
        inputLayout.hideError()
    }

    override fun isRequired(): Boolean {
        return required
    }

    override fun isValid(): Boolean {
        return valid
    }

    override fun setForm(form: FormConstraintLayout) {
        this.form = form
    }
}