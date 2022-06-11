package com.msg.gcms.ui.base

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import com.msg.gcms.R
import com.msg.gcms.databinding.DetailDialogBinding

class BaseDialog(context: Context, private val layoutId: Int) : Dialog(context){
    private val dialog = Dialog(context)

    fun showDialog() {
        dialog.setContentView(layoutId)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()
    }
}