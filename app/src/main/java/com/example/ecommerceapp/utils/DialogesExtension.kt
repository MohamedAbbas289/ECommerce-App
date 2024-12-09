package com.example.ecommerceapp.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.Fragment

fun Activity.showMessage(
    message: String,
    posActionName: String? = null,
    posAction: OnDialogActionClickListener? = null,
    negActionName: String? = null,
    negAction: OnDialogActionClickListener? = null,
    isCancelable: Boolean = true
): AlertDialog? {

    val dialogBuilder = AlertDialog.Builder(this)
        .setMessage(message)
    if (posActionName != null) {
        dialogBuilder.setPositiveButton(
            posActionName
        ) { dialog, id ->
            dialog.dismiss()
            posAction?.onDialogActionClick()
        }
    }
    if (negActionName != null) {
        dialogBuilder.setNegativeButton(
            negActionName
        ) { dialog, id ->
            dialog.dismiss()
            negAction?.onDialogActionClick()
        }
    }
    dialogBuilder.setCancelable(isCancelable)
    return dialogBuilder.show()
}

fun Fragment.showMessage(
    message: String,
    posActionName: String? = null,
    posAction: DialogInterface.OnClickListener? = null,
    negActionName: String? = null,
    negAction: DialogInterface.OnClickListener? = null
): AlertDialog? {

    val dialogBuilder = AlertDialog.Builder(context)
        .setMessage(message)
    if (posActionName != null) {
        dialogBuilder.setPositiveButton(posActionName, posAction)
    }
    if (negActionName != null) {
        dialogBuilder.setNegativeButton(negActionName, negAction)
    }

    return dialogBuilder.show()
}