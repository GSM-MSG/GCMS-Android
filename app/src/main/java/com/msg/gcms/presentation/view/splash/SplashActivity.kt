package com.msg.gcms.presentation.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msg.gcms.R
import com.msg.gcms.di.GCMSApplication
import com.msg.gcms.presentation.view.intro.IntroActivity
import com.msg.gcms.presentation.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val currentTime = LocalDateTime.now()
    private val expriedAt = LocalDateTime.parse(GCMSApplication.prefs.refreshExp)
    private val isLogin: Boolean =
        currentTime.isAfter(expriedAt) && !GCMSApplication.prefs.accessToken.isNullOrEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(
            Intent(
                this@SplashActivity,
                (if (isLogin) MainActivity::class else IntroActivity::class).java
            )
        )
    }
}
