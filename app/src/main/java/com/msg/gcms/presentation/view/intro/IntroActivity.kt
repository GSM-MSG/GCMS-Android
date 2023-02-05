package com.msg.gcms.presentation.view.intro

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.msg.gauthsignin.GAuthButton
import com.msg.gauthsignin.GAuthSigninWebView
import com.msg.gauthsignin.component.utils.Types
import com.msg.gcms.BuildConfig
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityIntroBinding
import com.msg.gcms.presentation.base.BaseActivity
import com.msg.gcms.presentation.view.main.MainActivity
import com.msg.gcms.presentation.viewmodel.AuthViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import com.msg.gcms.ui.component.intro.component.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {

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
                colors = Types.Colors.OUTLINE
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
