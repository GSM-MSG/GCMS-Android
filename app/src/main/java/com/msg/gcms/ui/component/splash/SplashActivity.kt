package com.msg.gcms.ui.component.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.msg.gcms.R
import com.msg.gcms.ui.component.intro.IntroActivity
import com.msg.gcms.ui.component.main.MainActivity
import com.msg.viewmodel.RegistrationViewModel
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
