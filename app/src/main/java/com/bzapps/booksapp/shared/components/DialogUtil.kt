package com.bzapps.booksapp.shared.components

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.bzapps.booksapp.R

class DialogUtil {

    companion object {
        fun showAlertDialog(
            context: Context,
            title: String,
            message: String?,
            positiveBtnText: String,
            positiveBtnFunction: () -> Unit,
            negativeBtnText: String,
            negativeBtnFunction: () -> Unit,
        ) {
            val alertDialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)
            alertDialog.setTitle(title)

            if (message != null) {
                alertDialog.setMessage(message)
            }

            alertDialog.setPositiveButton(positiveBtnText) { _, _ ->
                positiveBtnFunction()
            }
            alertDialog.setNegativeButton(negativeBtnText) { dialog, _ ->
                dialog.dismiss()
                negativeBtnFunction()
            }

            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }
}


