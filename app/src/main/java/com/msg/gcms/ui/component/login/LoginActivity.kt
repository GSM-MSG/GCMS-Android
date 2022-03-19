package com.msg.gcms.ui.component.login

import android.content.Intent
import android.view.View
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityLoginBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.intro.IntroActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login),
    View.OnClickListener {

    override fun viewSetting() {
        binding.apply {
            backBtn.setOnClickListener(this@LoginActivity)
            findPasswordBtn.setOnClickListener(this@LoginActivity)
        }
    }

    override fun observeEvent() {

    }


    override fun onClick(v: View?) {
        when(v!!.id) {
            binding.backBtn.id -> {
                val introIntent = Intent(this, IntroActivity::class.java)
                startActivity(introIntent)
                finish()
            }
            binding.findPasswordBtn.id -> {
                shortToast("업데이트 예정입니다.")
            }
        }
    }
}