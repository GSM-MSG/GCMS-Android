package com.msg.gcms.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentEmailFailureDialogBinding

class EmailFailureDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentEmailFailureDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_email_failure_dialog,
            container,
            false
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.okBtn.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}