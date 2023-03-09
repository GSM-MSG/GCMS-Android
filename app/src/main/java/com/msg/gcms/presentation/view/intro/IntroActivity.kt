package com.msg.gcms.presentation.view.intro

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.ui.unit.dp
import com.msg.gauthsignin.GAuthSigninWebView
import com.msg.gauthsignin.component.GAuthButton
import com.msg.gauthsignin.component.utils.Types
import com.msg.gcms.BuildConfig
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityIntroBinding
import com.msg.gcms.presentation.base.BaseActivity
import com.msg.gcms.presentation.view.main.MainActivity
import com.msg.gcms.presentation.view.progress.ProgressDialog
import com.msg.gcms.presentation.viewmodel.AuthViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    private var backButtonWait: Long = 0
    private lateinit var progressDialog: ProgressDialog
    private val viewModel by viewModels<AuthViewModel>()

    override fun viewSetting() {
        binding.login = this
        progressDialog = ProgressDialog(this)
        setGAuthButtonComponent()
    }

    override fun observeEvent() {
        observeSignInEvent()
    }

    private fun setGAuthButtonComponent() {
        binding.signInBtn.setContent {
            GAuthButton(
                style = Types.Style.DEFAULT,
                actionType = Types.ActionType.SIGNIN,
                colors = Types.Colors.OUTLINE,
                horizontalPaddingValue = 80.dp
            ) {
                binding.gAuthWebView.visibility = View.VISIBLE
                setGAuthWebViewComponent()
            }
        }
    }

    private fun setGAuthWebViewComponent() {
        binding.gAuthWebView.setContent {
            GAuthSigninWebView(
                clientId = BuildConfig.CLIENT_ID,
                redirectUri = BuildConfig.REDIRECT_URI,
            ) {
                progressDialog.show()
                binding.gAuthWebView.visibility = View.INVISIBLE
                viewModel.postSignInRequest(code = it)
            }
        }
    }

    private fun observeSignInEvent() {
        viewModel.postSignInRequest.observe(this) { event ->
            progressDialog.dismiss()
            when (event) {
                Event.Success -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                else -> Toast.makeText(this, "알 수 없는 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onClickPageBtn(view: View) {
        when (view.id) {
            binding.termsOfServiceBtn.id -> {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://matsogeum.notion.site/db8c0669605e4685b036cc08293aceb7")
                    )
                )
            }
            binding.privacyPolicyBtn.id -> {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://matsogeum.notion.site/0f7c494b26114da098d0c8ea50bb5588")
                    )
                )
            }
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.filter { it is OnBackPressedListener }
            .map { it as OnBackPressedListener }
            .forEach { it.onBackPressed(); return }

        if (System.currentTimeMillis() - backButtonWait >= 2000) {
            backButtonWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            super.onBackPressed()
            moveTaskToBack(true)
            finishAndRemoveTask()
            exitProcess(0)
        }
    }

    interface OnBackPressedListener {
        fun onBackPressed()
    }
}
