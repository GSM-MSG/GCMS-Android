package com.msg.gcms.ui.component.club.home

import android.view.View
import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubHomeBinding
import com.msg.gcms.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentClubHomeBinding>(R.layout.fragment_club_home),
    View.OnClickListener {
    override fun init() {
        initClick()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn -> this.findNavController()
                .navigate(R.id.action_homeFragment_to_detailFragment)
        }
    }

    private fun initClick() {
        binding.btn.setOnClickListener(this)
    }
}
