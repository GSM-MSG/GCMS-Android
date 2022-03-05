package com.msg.gcms.ui.component.club

import androidx.fragment.app.viewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.MainViewModel

class ClubFragment: BaseFragment<FragmentClubBinding>(R.layout.fragment_club) {
    private val mainViewModel by viewModels<MainViewModel> ()
    override fun init() {
    }
}