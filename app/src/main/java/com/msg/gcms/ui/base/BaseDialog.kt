package com.msg.gcms.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.msg.gcms.databinding.DetailDialogBinding
class BaseDialog(tile: String, msg: String,val func: () -> Unit) : DialogFragment(){
    private lateinit var binding: DetailDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.ok.setOnClickListener {
            func()
        }
        binding.cancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}