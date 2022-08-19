package com.msg.gcms.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.msg.gcms.databinding.ErrorExeptionDialogBinding

class BaseModal(val title: String, val msg: String, context: Context) : Dialog(context) {

    lateinit var dialogBinding: ErrorExeptionDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialogBinding = ErrorExeptionDialogBinding.inflate(layoutInflater)
        setContentView(dialogBinding.root)
        viewSet()
    }

    private fun viewSet() = with(dialogBinding) {
        setCancelable(true)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        ok.setOnClickListener {
            dismiss()
        }
        dialogTitle.text = title
        dialogMsg.text = msg
    }
}