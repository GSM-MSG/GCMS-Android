package com.msg.gcms.presentation.view.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.msg.gcms.R
import com.msg.gcms.data.remote.dto.auth.response.SignInResponse
import com.msg.gcms.presentation.base.BaseModal
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
        //versionChecker = VersionChecker(this, afterLogic = { afterLogic() })
        afterLogic()
        startActivity()
    }

    private fun afterLogic() {
        splashViewModel.checkIsLogin(this)
    }

    private fun startActivity() {
        splashViewModel.isLoginAble.observe(this) {
            if (it) checkTokenIsNormal()
            else enterActivity(this@SplashActivity, IntroActivity(), true)
        }
    }

    private fun checkTokenIsNormal() {
        mainViewModel.getClubList()
        observeGetClubEvent()
    }

    private fun observeGetClubEvent() {
        mainViewModel.getClubList.observe(this) {
            when (it) {
                Event.Success -> enterActivity(this@SplashActivity, MainActivity(), true)
                Event.Unauthorized -> enterActivity(this@SplashActivity, IntroActivity(), true)
                else -> BaseModal("오류", "알수 없는 오류가 발생했습니다.", this).show()
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