package com.msg.gcms.ui.component.intro

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityIntroBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.main.MainActivity
import com.msg.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {

    private val viewModel by viewModels<RegistrationViewModel>()
    private lateinit var client: GoogleSignInClient

    override fun viewSetting() {
        binding.login = this
        clickGoogleLogin()
    }

    override fun observeEvent() {
        login()
        isLogin()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "Google id: ${account.idToken}")
                // viewModel.sendIdTokenLogic(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, e)
            }
        }
    }

    private fun login() {
        viewModel.idTokenStatus.observe(this, Observer {
            when (it) {
                in 200..299 -> startActivity(Intent(this, MainActivity::class.java))
                in 403..404 -> {
                    shortToast("학교 계정으로 로그인 해주세요.")
                    client.signOut()
                }
                else -> {
                    shortToast("로그인에 실패했습니다.")
                    client.signOut()
                }
            }
        })
    }

    private fun isLogin() {
        viewModel.apply {
            isLogin.observe(this@IntroActivity) {
                if (it) {
                    startActivity(Intent(this@IntroActivity, MainActivity::class.java))
                } else {
                    client.signOut()
                }
            }
        }
    }

    private fun clickGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(this, gso)
        binding.signInBtn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = client.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        private const val TAG = "google Login"
        private const val RC_SIGN_IN = 10
    }

    fun onClickPageBtn(view: View) {
        when(view.id) {
            binding.termsOfServiceBtn.id -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.notion.so/f4b4084f6235444bbcc164f7c5d86fb2")))
            }
            binding.privacyPolicyBtn.id -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.notion.so/252fc57341834617b7d3c1903286c730")))
            }
        }
    }
}
