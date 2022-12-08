package com.bzapps.booksapp.shared.components.form

interface IFormItem {

    fun isRequired(): Boolean

    fun isValid(): Boolean

    fun setForm(form: FormConstraintLayout)
}