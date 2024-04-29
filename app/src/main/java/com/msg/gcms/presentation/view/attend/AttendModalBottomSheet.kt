package com.msg.gcms.presentation.view.attend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.msg.gcms.databinding.AttendDialogBinding
import com.msg.gcms.presentation.viewmodel.AttendViewModel

class AttendModalBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding: AttendDialogBinding
    private val viewModel by activityViewModels<AttendViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = AttendDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.attendStudentTv.text = viewModel.selectedStudentName.getListString()
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
        with(binding) {
            attendBtn.setOnClickListener { it.onButtonClicked() }
            lateBtn.setOnClickListener { it.onButtonClicked() }
            absentBtn.setOnClickListener { it.onButtonClicked() }
            sickBtn.setOnClickListener { it.onButtonClicked() }
        }
    }

    private fun View.onButtonClicked() {
        when (this) {
            binding.attendBtn -> {
                viewModel.attendanceStatus.value = "ATTENDANCE"
            }
            binding.lateBtn -> {
                viewModel.attendanceStatus.value = "LATE"
            }
            binding.absentBtn -> {
                viewModel.attendanceStatus.value = "ABSENT"
            }
            binding.sickBtn -> {
                viewModel.attendanceStatus.value = "REASONABLE_ABSENT"
            }
        }
        viewModel.useAttendFun()
    }

    private fun List<String>.getListString(): String {
        var returnString = ""
        val loopNumbers = if (this.size >= 4) 4 else this.size
        for (i in 0..loopNumbers) {
            returnString += if (i == loopNumbers) this[i] else "${this[i]}, "
        }
        returnString += if (this.size > 4) "\n외 ${this.size - 4}명을 출석체크하시겠습니까?" else "\n님을 출석체크하시겠습니까?"
        return returnString
    }

    companion object {
        const val TAG = "AttendModalBottomSheet"
    }
}