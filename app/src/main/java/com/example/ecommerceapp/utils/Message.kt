package com.example.ecommerceapp.utils


data class Message(
    val message: String? = null,
    val posActionName: String? = null,
    val onPosActionClick: OnDialogActionClickListener? = null,
    val negActionName: String? = null,
    val onNegActionClick: OnDialogActionClickListener? = null,
    val isCancelable: Boolean = true
)

fun interface OnDialogActionClickListener {
    fun onDialogActionClick()
}
