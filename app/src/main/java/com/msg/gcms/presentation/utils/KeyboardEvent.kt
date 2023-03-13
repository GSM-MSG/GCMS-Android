package com.msg.gcms.presentation.utils

import android.app.Activity
import android.widget.EditText
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun keyboardHide(context: Activity, vararg viewList: EditText) {
    viewList.forEach { view ->
        view.clearFocus()
    }
    val window = context.window
    WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.ime())
}