package com.msg.gcms.ui.component.club.detail

import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubDetailBinding
import com.msg.gcms.ui.base.BaseFragment

class DetailFragment: BaseFragment<FragmentClubDetailBinding>(R.layout.fragment_club_detail) {
    override fun init() {
        clickBackBtn()
    }

    private fun clickBackBtn() {
        binding.backBtn.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }
}