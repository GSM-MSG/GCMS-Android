package com.msg.gcms.ui.component.login

import android.content.Intent
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
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
        setAnim()
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

    private fun setAnim(){
        binding.apply {
            wave1.startAnimation(AnimationUtils.loadAnimation(this@LoginActivity, R.anim.login_animation1))
            Handler().postDelayed({
                wave2.startAnimation(AnimationUtils.loadAnimation(this@LoginActivity, R.anim.login_animation2))
            }, 1000)
        }
    }
}