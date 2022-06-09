package com.msg.gcms.ui.base

import android.app.Dialog
import android.content.Context
import android.view.Window


class BaseDialog(context: Context, layoutId: Int) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(layoutId)
        setCancelable(false)
    }
}