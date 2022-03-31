package com.msg.gcms.ui.component.emailcheck

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleObserver
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentEmailCheckBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.RegistrationViewModel
import java.util.Observer


class EmailCheckFragment : BaseFragment<FragmentEmailCheckBinding>(R.layout.fragment_email_check),
    View.OnClickListener {

    private val registrationViewModel by activityViewModels<RegistrationViewModel>()

    override fun init() {
        viewSetting()
        bindState()
    }

    private fun viewSetting() {
        binding.apply {
            listOf(
                number0, number1, number2, number3, number4, number5, number6,
                number7, number8, number9, eraseBtn, backBtn
            ).forEach { it.setOnClickListener(this@EmailCheckFragment) }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            binding.number0.id -> addText(0)
            binding.number1.id -> addText(1)
            binding.number2.id -> addText(2)
            binding.number3.id -> addText(3)
            binding.number4.id -> addText(4)
            binding.number5.id -> addText(5)
            binding.number6.id -> addText(6)
            binding.number7.id -> addText(7)
            binding.number8.id -> addText(8)
            binding.number9.id -> addText(9)
            binding.eraseBtn.id -> eraseText()
            binding.backBtn.id -> {}
        }

    }

    private fun clearText(){

    }

    private fun addText(num: Int) {
        registrationViewModel.typeNumber(num)
    }

    private fun eraseText() {
        registrationViewModel.eraseNumber()
    }

    private fun bindState() {
        registrationViewModel.emailCode.observe(viewLifecycleOwner) { code ->

            val viewList = listOf(
                binding.emailCheckEdittext1,
                binding.emailCheckEdittext2,
                binding.emailCheckEdittext3,
                binding.emailCheckEdittext4
            )
            viewList.withIndex().forEach {
                it.value.text = if (it.index <= code.length - 1) code[it.index].toString() else null
            }
            if (code.length == 4) {
                with(registrationViewModel) { emailCheckLogic(code) }
            }
        }
    }
}