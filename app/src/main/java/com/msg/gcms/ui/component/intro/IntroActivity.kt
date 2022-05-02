package com.msg.gcms.ui.component.intro


import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityIntroBinding
import com.msg.gcms.di.GCMSApplication
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.login.LoginActivity
import com.msg.gcms.ui.component.main.MainActivity
import com.msg.gcms.ui.component.registration.RegistrationActivity
import com.msg.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    private val loginViewModel by viewModels<LoginViewModel>()
    override fun viewSetting() {
        Log.d("TAG", "viewSetting: ${GCMSApplication.prefs.refreshToken}")
    }

    override fun observeEvent() {
        clickLogin()
        clickRegistration()
        checkLogin()
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

    private fun checkLogin(){
        loginViewModel.checkLogin()
        loginViewModel.loginStatus.observe(this){
            if(it){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}