package com.msg.gcms.ui.component.club.home

import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubHomeBinding
import com.msg.gcms.ui.base.BaseFragment

class HomeFragment: BaseFragment<FragmentClubHomeBinding>(R.layout.fragment_club_home) {
    override fun init() {
        click()
    }

    private fun click() {
        binding.clubHomeRecyclerview.setOnClickListener {
            this.findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
    }
}