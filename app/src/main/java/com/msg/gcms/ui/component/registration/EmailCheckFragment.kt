package com.msg.gcms.ui.component.registration


import android.view.View
import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentEmailCheckBinding
import com.msg.gcms.ui.base.BaseFragment


class EmailCheckFragment : BaseFragment<FragmentEmailCheckBinding>(R.layout.fragment_email_check), View.OnClickListener {
    override fun init() {
        viewSetting()

    }

    private fun viewSetting() {
        binding.apply {
            backBtn.setOnClickListener(this@EmailCheckFragment)
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            binding.backBtn.id -> {
                this.findNavController().navigate(R.id.action_emailCheckFragment_to_signUpFragment)
            }
        }
    }
}