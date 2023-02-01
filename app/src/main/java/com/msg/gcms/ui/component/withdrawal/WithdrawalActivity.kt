package com.msg.gcms.ui.component.withdrawal

import androidx.activity.viewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityWithdrawalBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.viewmodel.WithdrawalViewModel
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
            TODO("GAuth에 맞는 방식으로 변경 필요")
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