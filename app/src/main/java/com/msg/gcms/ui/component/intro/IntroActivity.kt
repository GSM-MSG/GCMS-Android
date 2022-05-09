package com.msg.gcms.ui.component.intro

import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityIntroBinding
import com.msg.gcms.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    override fun viewSetting() {
    }

    override fun observeEvent() {
        clickGoogleLogin()
    }

    private fun clickGoogleLogin() {
        binding.signInBtn.setOnClickListener {

        }
    }
}
