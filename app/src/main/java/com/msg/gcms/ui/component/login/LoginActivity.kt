package com.msg.gcms.ui.component.login

import android.content.Intent
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityLoginBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.intro.IntroActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login),
    View.OnClickListener {

    override fun viewSetting() {
        binding.apply {
            backBtn.setOnClickListener(this@LoginActivity)
            findPasswordBtn.setOnClickListener(this@LoginActivity)
            loginBtn.setOnClickListener(this@LoginActivity)
        }
        setAnim()
        setTyping()
    }

    override fun observeEvent() {

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            binding.backBtn.id -> {
                val introIntent = Intent(this, IntroActivity::class.java)
                startActivity(introIntent)
                finish()
            }
            binding.findPasswordBtn.id -> {
                shortToast("업데이트 예정입니다.")
            }
            binding.loginBtn.id -> {
                binding.errorMessage.visibility = View.VISIBLE
                binding.idEditText.background = getDrawable(R.drawable.bg_login_edit_text_error)
                binding.textInputLayout.startAnimation(
                    AnimationUtils.loadAnimation(
                        this@LoginActivity,
                        R.anim.edit_text_error_animation
                    )
                )
                binding.pwEditText.background = getDrawable(R.drawable.bg_login_edit_text_error)
                binding.textInputLayout2.startAnimation(
                    AnimationUtils.loadAnimation(
                        this@LoginActivity,
                        R.anim.edit_text_error_animation
                    )
                )
                Handler().postDelayed({
                    binding.errorMessage.visibility = View.GONE
                    binding.idEditText.background = getDrawable(R.drawable.bg_login_edit_text)
                    binding.pwEditText.background = getDrawable(R.drawable.bg_login_edit_text)
                }, 500)
            }
        }
    }

    private fun setAnim() {
        binding.apply {
            wave1.startAnimation(
                AnimationUtils.loadAnimation(
                    this@LoginActivity,
                    R.anim.login_animation1
                )
            )
            Handler().postDelayed({
                wave2.startAnimation(
                    AnimationUtils.loadAnimation(
                        this@LoginActivity,
                        R.anim.login_animation2
                    )
                )
            }, 800)
        }
    }

    private fun setTyping() {
        binding.idEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.textInputLayout.isHintEnabled = p0!!.length <= 0
            }
        })
        binding.pwEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.textInputLayout2.isHintEnabled = p0!!.length <= 0
            }
        })
    }
}