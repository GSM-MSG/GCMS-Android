package com.msg.gcms.ui.component.club.detail

import android.view.View
import androidx.fragment.app.activityViewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubDetailBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.gcms.ui.component.club.ClubFragment
import com.msg.viewmodel.ClubDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentClubDetailBinding>(R.layout.fragment_club_detail) {
    private val viewModel by activityViewModels<ClubDetailViewModel>()
    override fun init() {
        viewModel.getDetail()
    }

    fun clickBtn(view: View) {
        when(view.id) {
            binding.backBtn.id -> parentFragmentManager.beginTransaction().replace(R.id.fragment_club, ClubFragment()).commit()

            binding.sideBarBtn.id -> {

            }
        }
    }
}
