package com.msg.gcms.presentation.view.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.msg.gcms.R
import com.msg.gcms.presentation.utils.enterActivity
import com.msg.gcms.presentation.view.intro.IntroActivity
import com.msg.gcms.presentation.view.main.MainActivity
import com.msg.gcms.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel.checkAutoLoginAble(this)
        startActivity()
    }

    private fun startActivity() {
        splashViewModel.isLoginAble.observe(this) {
            enterActivity(
                this@SplashActivity,
                if (it)
                    MainActivity()
                else
                    IntroActivity()
            )
        }
    }
}
