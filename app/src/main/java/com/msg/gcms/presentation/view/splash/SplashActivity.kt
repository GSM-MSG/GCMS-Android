package com.msg.gcms.presentation.view.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.msg.gcms.R
import com.msg.gcms.presentation.utils.enterActivity
import com.msg.gcms.presentation.view.intro.IntroActivity
import com.msg.gcms.presentation.view.main.MainActivity
import com.msg.gcms.presentation.viewmodel.MainViewModel
import com.msg.gcms.presentation.viewmodel.SplashViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val splashViewModel by viewModels<SplashViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()
    //private lateinit var versionChecker: VersionChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel.checkIsLogin(this)
        //versionChecker = VersionChecker(this, afterLogic = { afterLogic() })
        observeIsLogin()
    }

    private fun observeIsLogin() {
        splashViewModel.isLogin.observe(this) {
            when (it) {
                Event.Success -> enterActivity(this@SplashActivity, MainActivity(), true)
                else -> enterActivity(this@SplashActivity, IntroActivity(), true)
            }
        }
    }
    //
    // override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    //     super.onActivityResult(requestCode, resultCode, data)
    //     versionChecker.onActivityResult(requestCode, resultCode, data)
    // }
    //
    // override fun onResume() {
    //     super.onResume()
    //     versionChecker.onResume()
    // }
    //
    // override fun onDestroy() {
    //     super.onDestroy()
    //     versionChecker.onDestroy()
    // }
}