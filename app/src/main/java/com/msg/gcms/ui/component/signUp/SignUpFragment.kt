package com.msg.gcms.ui.component.signUp

import android.view.View
import androidx.fragment.app.activityViewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentSignUpBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.RegistrationViewModel

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up), View.OnClickListener {

    private val registrationViewModel by activityViewModels<RegistrationViewModel>()

    private fun viewSetting() {
        binding.apply {
            backBtn.setOnClickListener(this@SignUpFragment)
            emailAccessBtn.setOnClickListener(this@SignUpFragment)
        }
    }

    override fun init() {
        viewSetting()
    }



    private fun editTextCheck() {
        if (binding.emailEt.text!!.isEmpty()) {
            binding.textInputLayout.error = "이메일을 입력해주세요"
        } else if (binding.emailEt.text!!.isNotEmpty()) {
            binding.textInputLayout.error = null
        }
        if (binding.passwordEt.text!!.isEmpty()) {
            binding.textInputLayout2.error = "비밀번호를 입력해주세요"
        } else if (binding.passwordEt.text!!.isNotEmpty()) {
            binding.textInputLayout2.error = null
        }
        if (binding.emailEt.text!!.isNotEmpty() && binding.passwordEt.text!!.isNotEmpty()) {
            registrationLogic(binding.emailEt.text.toString(), binding.passwordEt.text.toString())
        }
    }

    private fun registrationLogic(email : String, password : String) {
        registrationViewModel.registrationLogic(email, password)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            binding.backBtn.id -> {

            }
            binding.emailAccessBtn.id ->
                editTextCheck()
        }
    }
}