package com.msg.gcms.presentation.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.msg.gcms.R

import com.msg.gcms.presentation.view.main.MainActivity
import com.msg.gcms.presentation.viewmodel.RegistrationViewModel
import com.msg.gcms.presentation.view.intro.IntroActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegistrationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        isLogin()
    }

    private fun isLogin() {
        viewModel.apply {
            // checkLogin()
            isLogin.observe(this@SplashActivity) {
                startActivity(
                    Intent(
                        this@SplashActivity,
                        (if (it) MainActivity::class else IntroActivity::class).java
                    )
                )
            }
        }
    }
}
