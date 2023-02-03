package com.msg.gcms.presentation.view.intro

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.msg.gauthsignin.GAuthButton
import com.msg.gauthsignin.GAuthSigninWebView
import com.msg.gauthsignin.component.utils.Types
import com.msg.gcms.BuildConfig
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityIntroBinding
import com.msg.gcms.presentation.base.BaseActivity
import com.msg.gcms.presentation.viewmodel.AuthViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {

    private val viewModel by viewModels<AuthViewModel>()

    override fun viewSetting() {
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
                Log.d("TAG",it)
                viewModel.postSignInRequest(code = it)
            }
        }
    }

    private fun observeSignInEvent() {
        viewModel.postSignInRequest.observe(this) { event ->
            when (event) {
                Event.BadRequest -> Log.d("TAG",event.toString())
                Event.Conflict -> Log.d("TAG",event.toString())
                Event.ForBidden -> Log.d("TAG",event.toString())
                Event.NotAcceptable -> Log.d("TAG",event.toString())
                Event.NotFound -> Log.d("TAG",event.toString())
                Event.Server -> Log.d("TAG",event.toString())
                Event.Success -> Log.d("TAG",event.toString())
                Event.TimeOut -> Log.d("TAG",event.toString())
                Event.UnKnown -> Log.d("TAG",event.toString())
                Event.Unauthorized -> Log.d("TAG",event.toString())
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
