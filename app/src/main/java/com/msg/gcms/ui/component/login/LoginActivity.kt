package com.msg.gcms.ui.component.login



import android.content.Intent
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityLoginBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.main.MainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
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