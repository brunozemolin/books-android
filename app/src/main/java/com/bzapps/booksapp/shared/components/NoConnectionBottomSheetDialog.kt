package com.bzapps.booksapp.shared.components

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bzapps.booksapp.databinding.ViewFullBottomSheetNoConnectionBinding
import com.bzapps.booksapp.shared.components.bottomsheet.BaseBottomSheetDialogFragment

class NoConnectionBottomSheetDialog :
    BaseBottomSheetDialogFragment() {

        private val binding by lazy { ViewFullBottomSheetNoConnectionBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun setupView() {
        initListener()
    }

    private fun initListener() {
        this.view?.isFocusableInTouchMode = true
        this.view?.requestFocus()
        this.view?.setOnKeyListener { _, keyCode, _ ->
            keyCode == KeyEvent.KEYCODE_BACK
        }
    }
}