package com.msg.gcms.presentation.view.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.msg.gcms.R
import com.msg.gcms.domain.exception.NoInternetException
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
        observeEvent()
        internetConnectionChecker()
        //versionChecker = VersionChecker(this, afterLogic = { afterLogic() })
    }

    private fun observeEvent() {
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

    private fun internetConnectionChecker() {
        try {
            checkIsInterConnected()
        } catch (e: NoInternetException) {
            BaseModal("에러", "인터넷 연결을 확인해주세요.", this).let { dialog ->
                dialog.show()
                dialog.dialogBinding.ok.setOnClickListener {
                    finish()
                    startActivity(Intent(this@SplashActivity, SplashActivity::class.java))
                }
            }
        }
    }

    private fun checkIsInterConnected() {
        val connectivityManager: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (networkInfo == null) throw NoInternetException() else splashViewModel.checkIsLogin(this)
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