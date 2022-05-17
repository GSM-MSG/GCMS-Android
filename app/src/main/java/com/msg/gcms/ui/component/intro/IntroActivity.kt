package com.msg.gcms.ui.component.intro

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.Auth.GoogleSignInApi
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.internal.zzi.getSignInResultFromIntent
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
    private val RC_SIGN_IN = 10
    private val TAG = "googleLogin"

    override fun viewSetting() {
    }

    override fun observeEvent() {
        clickGoogleLogin()
        observer()
    }

    private fun observer() {
        viewModel.idTokenStatus.observe(this, Observer {
            when (it) {
                in 200..299 -> startActivity(Intent(this, MainActivity::class.java))
                else -> shortToast("login failed")
            }
        })
    }

    private fun clickGoogleLogin() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        client = GoogleSignIn.getClient(this, gso)
        binding.signInBtn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = client.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                viewModel.sendIdTokenLogic(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
}
