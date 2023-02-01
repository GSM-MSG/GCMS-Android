package com.msg.gcms.presentation.view.withdrawal

import androidx.activity.viewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityWithdrawalBinding
import com.msg.gcms.presentation.base.BaseActivity
import com.msg.gcms.presentation.viewmodel.WithdrawalViewModel
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
        isWithdrawal()
    }

    private fun isWithdrawal() {
        viewModel.isWithdrawal.observe(this) {

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
            if (it.isActivated) {
                viewModel.withdrawal()
            }
        }
    }
}