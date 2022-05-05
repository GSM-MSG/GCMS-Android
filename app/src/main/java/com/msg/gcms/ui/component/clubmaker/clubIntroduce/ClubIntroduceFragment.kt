package com.msg.gcms.ui.component.clubmaker.clubIntroduce

import android.view.View
import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubIntroduceBinding
import com.msg.gcms.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubIntroduceFragment :
    BaseFragment<FragmentClubIntroduceBinding>(R.layout.fragment_club_introduce) {
    override fun init() {
        binding.fragment = this
    }

    fun clickedButton(view: View) {
        when (view.id) {
            binding.backBtn.id -> this.findNavController().popBackStack()
            binding.nextBtn.id -> this.findNavController()
                .navigate(R.id.action_clubIntroduceFragment_to_makeClubDetailFragment)
        }
    }
}
