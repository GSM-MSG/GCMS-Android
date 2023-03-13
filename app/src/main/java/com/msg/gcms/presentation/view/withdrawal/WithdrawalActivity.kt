package com.msg.gcms.presentation.view.withdrawal

import android.widget.Toast
import androidx.activity.viewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityWithdrawalBinding
import com.msg.gcms.presentation.base.BaseActivity
import com.msg.gcms.presentation.utils.enterActivity
import com.msg.gcms.presentation.view.intro.IntroActivity
import com.msg.gcms.presentation.viewmodel.WithdrawalViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawalActivity : BaseActivity<ActivityWithdrawalBinding>(R.layout.activity_withdrawal) {
    private val viewModel by viewModels<WithdrawalViewModel>()

    override fun viewSetting() {
        clickCheckBox()
        withdrawal()
        backBtnClick()
    }

    override fun observeEvent() {
        isChecked()
        observeWithDrawalEvent()
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
            if (it.isActivated) {
                viewModel.withdrawal()
            }
        }
    }

    private fun observeWithDrawalEvent() {
        viewModel.withDrawalRequest.observe(this) {
            when (it) {
                Event.Success ->
                    enterActivity(this@WithdrawalActivity, IntroActivity())
                Event.BadRequest ->
                    Toast.makeText(this@WithdrawalActivity, "동아리 부장인 경우 탈퇴할 수 없습니다.", Toast.LENGTH_SHORT).show()
                else ->
                    Toast.makeText(this@WithdrawalActivity, "알 수 없는 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}