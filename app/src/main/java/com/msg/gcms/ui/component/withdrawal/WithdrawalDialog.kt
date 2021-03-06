package com.msg.gcms.ui.component.withdrawal

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.msg.gcms.R
import com.msg.gcms.databinding.LogoutDialogBinding

class WithdrawalDialog(context: Context) : BottomSheetDialog(context) {
    lateinit var withdrawalDialogListener: WithdrawalDialogListener
    lateinit var binding: LogoutDialogBinding

    interface WithdrawalDialogListener {
        fun logout()
        fun goWithdrawal()
    }

    fun setDialogListener(withdrawalDialogListener: WithdrawalDialogListener) {
        this.withdrawalDialogListener = withdrawalDialogListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LogoutDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog()
    }

    fun dialog() = with(binding) {
        setCanceledOnTouchOutside(true)
        window!!.apply {
            setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            setGravity(Gravity.BOTTOM)
        }
        logoutBtn.setOnClickListener {
            withdrawalDialogListener.logout()
            this@WithdrawalDialog.dismiss()
        }
        withdrawalBtn.setOnClickListener {
            withdrawalDialogListener.goWithdrawal()
            this@WithdrawalDialog.dismiss()
        }
    }
}