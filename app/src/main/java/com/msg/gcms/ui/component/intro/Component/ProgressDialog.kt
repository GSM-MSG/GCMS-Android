package com.msg.gcms.ui.component.intro.Component

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import com.msg.gcms.R

class ProgressDialog(context: Context) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.progress_dialog)
        setCancelable(true)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
}