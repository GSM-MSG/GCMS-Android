package com.msg.gcms.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.msg.gcms.databinding.DetailDialogBinding
import com.msg.viewmodel.ClubViewModel

class BaseDialog(val title: String, val msg: String  ) : DialogFragment() {

    lateinit var dialogBinding: DetailDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogBinding = DetailDialogBinding.inflate(inflater, container, false)
        return dialogBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(dialogBinding){
            dialogTitle.text = title
            dialogMsg.text = msg
            cancel.setOnClickListener {
                dismiss()
            }
        }
    }
}