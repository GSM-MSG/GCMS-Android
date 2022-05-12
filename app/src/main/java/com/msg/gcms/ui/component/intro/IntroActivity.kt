package com.msg.gcms.ui.component.intro

import android.view.animation.AnimationUtils
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityIntroBinding
import com.msg.gcms.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    override fun viewSetting() {
        settingAnimation()
    }

    override fun observeEvent() {
        clickGoogleLogin()
    }

    private fun clickGoogleLogin() {
        binding.signInBtn.setOnClickListener {

        }
    }

    private fun settingAnimation(){
        binding.imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.intro_animation))
        binding.introTxt.startAnimation(AnimationUtils.loadAnimation(this, R.anim.intro_animation))
        binding.introTxt2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.intro_animation))
    }
}
