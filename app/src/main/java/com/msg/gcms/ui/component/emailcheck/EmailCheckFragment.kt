package com.msg.gcms.ui.component.emailcheck

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
            backBtn.setOnClickListener(this@EmailCheckFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            binding.number0.id -> changeText(0)
            binding.number1.id -> changeText(1)
            binding.number2.id -> changeText(2)
            binding.number3.id -> changeText(3)
            binding.number4.id -> changeText(4)
            binding.number5.id -> changeText(5)
            binding.number6.id -> changeText(6)
            binding.number7.id -> changeText(7)
            binding.number8.id -> changeText(8)
            binding.number9.id -> changeText(9)
            binding.eraseBtn.id -> eraseText()
            binding.backBtn.id -> {}
        }
    }

    private fun changeText(num: Int) {
        if (binding.emailCheckEdittext1.text.isEmpty()) {
            binding.emailCheckEdittext1.text = num.toString()
        } else {
            if (binding.emailCheckEdittext2.text.isEmpty()) {
                binding.emailCheckEdittext2.text = num.toString()
            } else {
                if (binding.emailCheckEdittext3.text.isEmpty()) {
                    binding.emailCheckEdittext3.text = num.toString()
                } else {
                    if (binding.emailCheckEdittext4.text.isEmpty()) {
                        binding.emailCheckEdittext4.text = num.toString()
                        getCode()
                    }
                }
            }
        }
    }

    private fun eraseText() {
        if (binding.emailCheckEdittext4.text.isNotEmpty()) {
            binding.emailCheckEdittext4.text = null
        } else {
            if (binding.emailCheckEdittext3.text.isNotEmpty()) {
                binding.emailCheckEdittext3.text = null
            } else {
                if (binding.emailCheckEdittext2.text.isNotEmpty()) {
                    binding.emailCheckEdittext2.text = null
                } else {
                    if (binding.emailCheckEdittext1.text.isNotEmpty()) {
                        binding.emailCheckEdittext1.text = null
                    }
                }
            }
        }
    }

    private fun getCode() {
        val code1 = binding.emailCheckEdittext1.text.toString()
        val code2 = binding.emailCheckEdittext2.text.toString()
        val code3 = binding.emailCheckEdittext3.text.toString()
        val code4 = binding.emailCheckEdittext4.text.toString()

        val emailCheckCode = code1 + code2 + code3 + code4
        Log.d("EmailCode", emailCheckCode)

        registrationViewModel.emailCheckLogic(emailCheckCode)
    }
}