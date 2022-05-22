package com.msg.gcms.ui.component.intro

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            signInResult(task)
        }
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
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
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

    private fun signInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken.toString()
            Log.d(TAG, "idToken: $idToken")
            viewModel.sendIdTokenLogic(idToken)
        } catch (e: ApiException) {
            Log.e(TAG, e.toString())
        }
    }
}
