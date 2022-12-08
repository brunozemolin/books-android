package com.bzapps.booksapp.shared.components.form

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.bzapps.booksapp.R

class FormConstraintLayout(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private val DEFAULT_ID: Int = -1
    var allFields: MutableList<IFormItem> = mutableListOf()
    private val validators: List<CustomFormValidator> = ArrayList()

    private var submitButtonId = 0

    var submitButton: AppCompatButton? = null

    init {
        initialize(attrs)
    }


    private fun initialize(attributeSet: AttributeSet) {
        loadAttributes(attributeSet)
        post { this.loadFields() }
    }

    private fun loadFields() {
        allFields = ArrayList()
        addChildFields(this)
        if (submitButtonId != DEFAULT_ID) {
            submitButton = findViewById(submitButtonId)
            updateValidation()
        }
    }

    private fun addChildFields(container: ViewGroup) {
        for (i in 0 until container.childCount) {
            val child = container.getChildAt(i)
            if (child.visibility == GONE) continue
            if (child is IFormItem) {
                (child as IFormItem).setForm(this)
                allFields.add(child as IFormItem)
            } else if (child is ViewGroup) {
                addChildFields(child)
            }
        }
    }

    private fun isFormValid(): Boolean {
        val fieldsValid: Boolean = allFields.all { it.isValid() || !it.isRequired() }
        val customValid = validators.isEmpty() || validators.all { it.validate() }
        return fieldsValid && customValid
    }

    fun updateValidation() {
        if (submitButton != null) {
            submitButton!!.isEnabled = isFormValid()
        }
    }

    private fun loadAttributes(attributeSet: AttributeSet) {
        val attributes =
            context.obtainStyledAttributes(attributeSet, R.styleable.FormConstraintLayout)
        this.submitButtonId =
            attributes.getResourceId(R.styleable.FormConstraintLayout_formSubmitButton, DEFAULT_ID)
        attributes.recycle()
    }

    interface CustomFormValidator {
        fun validate(): Boolean
    }
}