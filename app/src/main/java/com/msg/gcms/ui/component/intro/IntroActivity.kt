package com.msg.gcms.ui.component.intro

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.viewModels
import com.msg.gauthsignin.GAuthButton
import com.msg.gauthsignin.GAuthSigninWebView
import com.msg.gauthsignin.component.utils.Types
import com.msg.gcms.BuildConfig
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityIntroBinding
import com.msg.gcms.ui.base.BaseActivity
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
                binding.gAuthWebView.visibility = View.VISIBLE
                binding.gAuthWebView.setContent {
                    GAuthSigninWebView(
                        clientId = BuildConfig.CLIENT_ID,
                        redirectUri = BuildConfig.REDIRECT_URI
                    ) { code ->
                        viewModel.setGAuthCode(code = code)
                        binding.gAuthWebView.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    override fun observeEvent() {
        isCodeArrive()
    }

    private fun isCodeArrive() = viewModel.apply {
        gAuthCode.observe(this@IntroActivity) {
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
