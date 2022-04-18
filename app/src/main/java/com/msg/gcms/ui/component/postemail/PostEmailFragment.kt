package com.msg.gcms.ui.component.postemail

import android.content.Intent
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentPostEmailBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.gcms.ui.component.intro.IntroActivity
import com.msg.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostEmailFragment : BaseFragment<FragmentPostEmailBinding>(R.layout.fragment_post_email) {

    private val registrationViewModel by activityViewModels<RegistrationViewModel>()

    override fun init() {
        binding.fragment = this
        setAnim()
    }

    fun clickedBtn(view: View) {
        when (view.id) {
            binding.backBtn.id -> {
                startActivity(Intent(activity, IntroActivity::class.java))
                activity?.finish()
            }
            binding.emailAccessBtn.id -> {
                postEmailLogic()
            }
        }
    }

    private fun postEmailLogic() {
         registrationViewModel.postEmailLogic(binding.emailEt.text.toString())
        this.findNavController().navigate(R.id.action_postEmailFragment_to_emailCheckFragment)
    }

    private fun setAnim() {
        binding.apply {
            wave1.startAnimation(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.login_animation1
                )
            )
            Handler().postDelayed({
                wave2.startAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.login_animation2
                    )
                )
            }, 800)
        }
    }


}