package com.msg.gcms.ui.component.clubmaker.clubDetail

import android.view.View
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentMakeClubDetailBinding
import com.msg.gcms.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeClubDetailFragment :
    BaseFragment<FragmentMakeClubDetailBinding>(R.layout.fragment_make_club_detail) {
    override fun init() {
        binding.fragment = this
    }

    fun clickedNextBtn(view : View) {
        activity?.finish()
    }
}