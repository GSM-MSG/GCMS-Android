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

    private val viewModel by activityViewModels<ClubDetailViewModel>()

    override fun init() {
        clickBackBtn()
        initResult()
        checkRole()
    }

    private fun clickBackBtn() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initResult() {
        viewModel.result.let {
            //club
            binding.clubName.text = it.body()!!.club.title
            binding.explainClubTxt.text = it.body()!!.club.description
            binding.link.text = it.body()!!.club.relatedLink[1].toString()
            //boss
            binding.bossImg.load(it.body()!!.head.userImg)
            binding.boss.text = it.body()!!.head.name
            //teacher
            binding.teacher.text = it.body()!!.club.teacher
            //contact
            binding.directoryTxt.text = it.body()!!.club.contact
            //banner
            binding.clubBanner.load(it.body()!!.club.bannerUrl)
        }
    }

    private fun checkRole() {
        when (viewModel.result.body()!!.scope) {
            "HEAD" -> binding.submitBtn.setBackgroundResource(R.drawable.ic_close_application_btn)
            "USER" -> {
                if (viewModel.result.body()!!.isApplied) {
                    binding.submitBtn.setBackgroundResource(R.drawable.ic_application_cancel_btn)
                } else if (!viewModel.result.body()!!.club.isOpened) {
                    binding.submitBtn.setBackgroundResource(R.drawable.ic_club_application_closed)
                    binding.submitBtn.isEnabled = false
                }
            }
            "MEMBER" -> {
                if(!viewModel.result.body()!!.club.isOpened){
                    binding.submitBtn.setBackgroundResource(R.drawable.ic_club_application_closed)
                } else {
                    binding.submitBtn.isGone
                }
            }
        }
    }
}
