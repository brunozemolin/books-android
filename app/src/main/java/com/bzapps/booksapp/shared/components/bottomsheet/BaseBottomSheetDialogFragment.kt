package com.bzapps.booksapp.shared.components.bottomsheet

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog

            val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            if (bottomSheet != null) {
                disableBottomSheetDragMotion(bottomSheet)
                setBottomSheetHeightToFullScreen(bottomSheet)
            }
        }

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    protected abstract fun setupView()

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        setupView()
    }

    private fun disableBottomSheetDragMotion(bottomSheet: FrameLayout) {
        BottomSheetBehavior.from(bottomSheet)
            .addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        BottomSheetBehavior.from(bottomSheet).state =
                            BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
    }

    private fun setBottomSheetHeightToFullScreen(bottomSheet: FrameLayout) {
        BottomSheetBehavior.from(bottomSheet).peekHeight =
            Resources.getSystem().displayMetrics.heightPixels
        BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED

        val layoutParams = bottomSheet.layoutParams
        if (layoutParams != null) {
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        }
        bottomSheet.layoutParams = layoutParams
    }
}