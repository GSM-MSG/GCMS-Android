package com.msg.gcms.ui.component.intro

import android.content.Intent
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityIntroBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.login.LoginActivity
import com.msg.gcms.ui.component.registration.RegistrationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    override fun viewSetting() {
    }

    override fun observeEvent() {
        clickLogin()
        clickRegistration()
    }

    private fun clickLogin() {
        binding.loginBtn.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(loginIntent)
        }
    }

    private fun clickRegistration() {
        binding.signInBtn.setOnClickListener {
            val registrationIntent = Intent(this, RegistrationActivity::class.java)
            registrationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(registrationIntent)
        }
    }
}
