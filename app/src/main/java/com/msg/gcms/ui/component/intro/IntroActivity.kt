package com.msg.gcms.ui.component.intro

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.msg.gauthsignin.GAuthButton
import com.msg.gauthsignin.GAuthSigninWebView
import com.msg.gauthsignin.component.utils.Types
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityIntroBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.main.MainActivity
import com.msg.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    private val viewModel by viewModels<RegistrationViewModel>()

    override fun viewSetting() {
        binding.login = this
        binding.signInBtn.setContent {
            GAuthButton(
                style = Types.Style.DEFAULT,
                actionType = Types.ActionType.CONTINUE,
                colors = Types.Colors.OUTLINE
            ) {
                setContent {
                    GAuthSigninWebView(
                        clientId = "00ce71cc5f774d4191db789d4e6aea40260080b4498947de98f3c7bd7d5ec78d",
                        redirectUri = "https://www.google.com"
                    ) { code ->
                        viewModel.setGAuthCode(code = code)
                    }
                    if (viewModel.isLoginInProgress.value)
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(50.dp),
                                color = Color.Black,
                                strokeWidth = 6.dp
                            )
                        }
                }
            }
        }
    }

    override fun observeEvent() {
        isLogin()
        isCodeArrive()
    }

    private fun isLogin() {
        viewModel.apply {
            checkLogin()
            isLogin.observe(this@IntroActivity) {
                if (it) {
                    startActivity(Intent(this@IntroActivity, MainActivity::class.java))
                } else {

                }
            }
        }
    }

    private fun isCodeArrive() = viewModel.apply {
        gAuthCode.observe(this@IntroActivity) {
            Log.d("code", it)
            isLoginInProgress.value = true
        }
    }

    fun onClickPageBtn(view: View) {
        when (view.id) {
            binding.termsOfServiceBtn.id -> {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.notion.so/f4b4084f6235444bbcc164f7c5d86fb2")
                    )
                )
            }
            binding.privacyPolicyBtn.id -> {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.notion.so/252fc57341834617b7d3c1903286c730")
                    )
                )
            }
        }
    }
}
