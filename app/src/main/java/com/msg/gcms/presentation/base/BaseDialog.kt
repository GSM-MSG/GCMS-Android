package com.msg.gcms.presentation.base

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.msg.gcms.databinding.DetailDialogBinding

class BaseDialog(val title: String, val msg: String, context: Context) : AlertDialog(context) {

    lateinit var dialogBinding: DetailDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialogBinding = DetailDialogBinding.inflate(layoutInflater)
        setContentView(dialogBinding.root)
        viewSet()
    }

    private fun viewSet() = with(dialogBinding) {
        setCancelable(true)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        cancel.setOnClickListener {
            dismiss()
        }
        dialogTitle.text = title
        dialogMsg.text = msg
    }
}