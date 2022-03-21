package com.msg.gcms.ui.component.login

import android.content.Intent
import android.view.View
import androidx.core.view.isEmpty
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityLoginBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.intro.IntroActivity
import com.msg.gcms.ui.component.main.MainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login),
    View.OnClickListener {

    override fun viewSetting() {
        binding.apply {
            backBtn.setOnClickListener(this@LoginActivity)
            findPasswordBtn.setOnClickListener(this@LoginActivity)
            loginBtn.setOnClickListener(this@LoginActivity)
        }
    }

    override fun observeEvent() {

    }

    private fun editTextCheck() {
        if (binding.emailEt.text!!.isEmpty()) {
            binding.textInputLayout.error = "이메일을 입력해주세요"
        }
        else if (binding.emailEt.text!!.isNotEmpty()) {
            binding.textInputLayout.error = null
        }
        if (binding.passwordEt.text!!.isEmpty()) {
            binding.textInputLayout2.error = "비밀번호를 입력해주세요"
        }
        else if (binding.passwordEt.text!!.isNotEmpty()) {
            binding.textInputLayout2.error = null
        }
        if (binding.emailEt.text!!.isNotEmpty() && binding.passwordEt.text!!.isNotEmpty()) {
            val mainIntent = Intent(this,MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            binding.backBtn.id -> {
                val introIntent = Intent(this, IntroActivity::class.java)
                startActivity(introIntent)
                finish()
            }
            binding.findPasswordBtn.id -> {
                shortToast("업데이트 예정입니다.")
            }
            binding.loginBtn.id ->
                editTextCheck()
        }
    }
}