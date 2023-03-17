package com.msg.gcms.util

import android.content.Context
import android.util.TypedValue

fun String.removeDot(): String {
    return this.replace("^\"|\"$".toRegex(), "")
}

fun Float.toDp(context: Context): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)
        .toInt()