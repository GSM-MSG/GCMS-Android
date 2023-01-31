package com.msg.gcms.presentation.component.withdrawal

import android.content.Intent
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityWithdrawalBinding
import com.msg.gcms.presentation.base.BaseActivity
import com.msg.gcms.presentation.component.intro.IntroActivity
import com.msg.gcms.presentation.viewmodel.WithdrawalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawalActivity : BaseActivity<ActivityWithdrawalBinding>(R.layout.activity_withdrawal) {
    private val viewModel by viewModels<WithdrawalViewModel>()
    lateinit var client: GoogleSignInClient
    override fun viewSetting() {
        clickCheckBox()
        withdrawal()
        backBtnClick()
    }

    override fun observeEvent() {
        isChecked()
        isWithdrawal()
    }

    private fun isWithdrawal(){
        viewModel.isWithdrawal.observe(this) {
            if(it) {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                client = GoogleSignIn.getClient(this, gso)
                client.signOut()
                val intent = Intent(this, IntroActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun clickCheckBox() {
        binding.checkbox.setOnClickListener {
            viewModel.changeIsApproved(binding.checkbox.isChecked)
        }
    }

    private fun isChecked() {
        viewModel.isApproved.observe(this) {
            binding.withdrawalBtn.isActivated = it
        }
    }

    private fun backBtnClick() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun withdrawal() {
        binding.withdrawalBtn.setOnClickListener {
            if(it.isActivated){
                // viewModel.withdrawal()
            }
        }
    }
}