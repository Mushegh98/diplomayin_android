package com.diplomayin.recognition.base.utils.extension

import android.content.Context
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager

fun View.changeVisibility() {
    visibility = if (visibility == VISIBLE) INVISIBLE else VISIBLE
}

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) VISIBLE else GONE
}

fun View.visibleOrInvisible(isVisible: Boolean) {
    visibility = if (isVisible) VISIBLE else INVISIBLE
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
