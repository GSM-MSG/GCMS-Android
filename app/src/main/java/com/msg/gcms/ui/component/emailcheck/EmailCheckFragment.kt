package com.msg.gcms.ui.component.emailcheck

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentEmailCheckBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.RegistrationViewModel


class EmailCheckFragment : BaseFragment<FragmentEmailCheckBinding>(R.layout.fragment_email_check),
    View.OnClickListener {

    private val registrationViewModel by activityViewModels<RegistrationViewModel>()

    override fun init() {
        viewSetting()
    }

    private fun viewSetting() {
        binding.apply {
            number0.setOnClickListener(this@EmailCheckFragment)
            number1.setOnClickListener(this@EmailCheckFragment)
            number2.setOnClickListener(this@EmailCheckFragment)
            number3.setOnClickListener(this@EmailCheckFragment)
            number4.setOnClickListener(this@EmailCheckFragment)
            number5.setOnClickListener(this@EmailCheckFragment)
            number6.setOnClickListener(this@EmailCheckFragment)
            number7.setOnClickListener(this@EmailCheckFragment)
            number8.setOnClickListener(this@EmailCheckFragment)
            number9.setOnClickListener(this@EmailCheckFragment)
            eraseBtn.setOnClickListener(this@EmailCheckFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            binding.number0.id -> {
                changeText(0)
            }
            binding.number1.id -> {
                changeText(1)
            }
            binding.number2.id -> {
                changeText(2)
            }
            binding.number3.id -> {
                changeText(3)
            }
            binding.number4.id -> {
                changeText(4)
            }
            binding.number5.id -> {
                changeText(5)
            }
            binding.number6.id -> {
                changeText(6)
            }
            binding.number7.id -> {
                changeText(7)
            }
            binding.number8.id -> {
                changeText(8)
            }
            binding.number9.id -> {
                changeText(9)
            }
            binding.eraseBtn.id -> {
                eraseText()
            }
        }
    }

    private fun changeText(num: Int) {

    }

    private fun eraseText() {

    }
}