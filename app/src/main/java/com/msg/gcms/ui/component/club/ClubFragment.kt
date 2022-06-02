package com.msg.gcms.ui.component.club

import android.content.Intent
import androidx.fragment.app.activityViewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.gcms.ui.component.clubmaker.MakeClubActivity
import com.msg.gcms.ui.component.profile.ProfileActivity
import com.msg.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubFragment : BaseFragment<FragmentClubBinding>(R.layout.fragment_club) {
    private val viewModel by activityViewModels<MainViewModel>()
    override fun init() {
        clickProfile()
        clickMakeClubBtn()
        clubTxt()
    }

    private fun clubTxt(){
        viewModel.getClubList()
        viewModel.clubName.observe(this){
            binding.clubNameTxt.text = viewModel.clubName.value
            viewModel.getClubList()
        }
    }

    private fun clickProfile() {
        binding.profileBtn.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }

    private fun clickMakeClubBtn() {
        binding.addClubBtn.setOnClickListener {
            val intent = Intent(activity, MakeClubActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }
}
