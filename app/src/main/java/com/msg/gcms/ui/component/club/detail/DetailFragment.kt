package com.msg.gcms.ui.component.club.detail

import androidx.core.view.isGone
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubDetailBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.ClubDetailViewModel
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
