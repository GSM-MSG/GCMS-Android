package com.msg.gcms.ui.component.intro


import android.content.Intent
import com.msg.gcms.R
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.main.MainActivity
import com.msg.gcms.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    override fun viewSetting() {

    }

    override fun observeEvent() {
        clickLogin()
    }

    private fun clickLogin() {
        binding.loginBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}