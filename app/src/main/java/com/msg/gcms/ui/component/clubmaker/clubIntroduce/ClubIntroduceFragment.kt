package com.msg.gcms.ui.component.clubmaker.clubIntroduce

import android.view.View
import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubIntroduceBinding
import com.msg.gcms.ui.base.BaseFragment

class ClubIntroduceFragment : BaseFragment<FragmentClubIntroduceBinding>(R.layout.fragment_club_introduce) {
    override fun init() {
        binding.fragment = this
    }

    fun clickedNextButton(view : View) {
        this.findNavController().navigate(R.id.action_clubIntroduceFragment_to_makeClubDetailFragment)
    }
}