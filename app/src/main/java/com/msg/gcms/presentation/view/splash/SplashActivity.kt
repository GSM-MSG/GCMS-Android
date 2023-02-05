package com.msg.gcms.presentation.view.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.msg.gcms.R
import com.msg.gcms.di.GCMSApplication
import com.msg.gcms.presentation.utils.enterActivity
import com.msg.gcms.presentation.view.intro.IntroActivity
import com.msg.gcms.presentation.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val currentTime = LocalDateTime.now()
    private lateinit var refreshExpriedAt: LocalDateTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity()
    }

    private fun startActivity() {
        refreshExpriedAt =
            if (!GCMSApplication.prefs.refreshExp.isNullOrEmpty())
                LocalDateTime.parse(
                    GCMSApplication.prefs.refreshExp!!.substring(0, 19)
                )
            else {
                currentTime
            }

        enterActivity(
            this@SplashActivity,
            if (currentTime.isAfter(refreshExpriedAt) || GCMSApplication.prefs.refreshExp.isNullOrEmpty())
                IntroActivity()
            else
                MainActivity()
        )
    }
}
