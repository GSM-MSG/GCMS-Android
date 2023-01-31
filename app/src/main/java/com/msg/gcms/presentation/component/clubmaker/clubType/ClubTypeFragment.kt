package com.msg.gcms.presentation.component.clubmaker.clubType

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubTypeBinding
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.viewmodel.MakeClubViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubTypeFragment : BaseFragment<FragmentClubTypeBinding>(R.layout.fragment_club_type) {

    private val makeClubViewModel by activityViewModels<MakeClubViewModel>()

    override fun init() {
        binding.fragment = this
    }

    private lateinit var clubType : String

    fun whenClickedBtn(view: View) {
        if (view.id != binding.clubTypeBackBtn.id) {
            when (view.id) {
                binding.majorBtn.id -> clubType = "MAJOR"
                binding.freeBtn.id -> clubType = "FREEDOM"
                binding.personalBtn.id -> clubType = "EDITORIAL"
            }
            makeClubViewModel.clubTypeChange(clubType)

            this.findNavController().navigate(R.id.action_clubTypeFragment_to_clubIntroduceFragment)
        } else if (view.id == binding.clubTypeBackBtn.id) {
            activity?.finish()
        }
    }
}
