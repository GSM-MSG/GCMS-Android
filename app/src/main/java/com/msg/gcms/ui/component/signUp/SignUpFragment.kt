package com.msg.gcms.ui.component.signUp

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentSignUpBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.RegistrationViewModel

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up),
    View.OnClickListener {

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
        binding.textInputLayout.error = if (binding.emailEt.text!!.isEmpty()) "이메일을 입력해주세요" else null
        binding.textInputLayout2.error = if (binding.passwordEt.text!!.isEmpty()) "비밀번호를 입력해주세요" else null
        if (binding.emailEt.text!!.isNotEmpty() && binding.passwordEt.text!!.isNotEmpty()) {
            registrationLogic(binding.emailEt.text.toString(), binding.passwordEt.text.toString())
        }
    }

    private fun registrationLogic(email: String, password: String) {
        registrationViewModel.registrationLogic(email, password)
        findNavController().navigate(R.id.action_signUpFragment_to_emailCheckFragment)
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