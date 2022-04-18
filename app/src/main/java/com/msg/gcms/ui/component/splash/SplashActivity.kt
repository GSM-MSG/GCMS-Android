package com.msg.gcms.ui.component.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.msg.gcms.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}