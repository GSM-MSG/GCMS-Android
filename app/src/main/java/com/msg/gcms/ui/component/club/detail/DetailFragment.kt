package com.msg.gcms.ui.component.club.detail

import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubDetailBinding
import com.msg.gcms.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentClubDetailBinding>(R.layout.fragment_club_detail) {
    override fun init() {
        clickBackBtn()
    }

    private fun clickBackBtn() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
