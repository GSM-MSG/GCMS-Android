package com.msg.gcms.ui.component.emailcheck

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentEmailCheckBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.RegistrationViewModel


class EmailCheckFragment : BaseFragment<FragmentEmailCheckBinding>(R.layout.fragment_email_check),
    View.OnClickListener {

    private val emailCode = ArrayList<String>()
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

    private fun addText(num: Int) {
        if (emailCode.size < 4) {
            emailCode.add(num.toString())
            setUi()
        }
    }


    private fun eraseText() {
        if (emailCode.isNotEmpty()) {
            emailCode.removeLast()
            setUi()
        }
    }

    private fun setUi() {
        if (emailCode.size < 1) {
            binding.emailCheckEdittext1.text = null
        } else {
            binding.emailCheckEdittext1.text = emailCode[0]
        }
        if (emailCode.size < 2) {
            binding.emailCheckEdittext2.text = null
        } else {
            binding.emailCheckEdittext2.text = emailCode[1]
        }
        if (emailCode.size < 3) {
            binding.emailCheckEdittext3.text = null
        } else {
            binding.emailCheckEdittext3.text = emailCode[2]
        }
        if (emailCode.size < 4) {
            binding.emailCheckEdittext4.text = null
        } else {
            binding.emailCheckEdittext4.text = emailCode[3]
        }
        Log.d("emailCode", emailCode.toString())
    }
}