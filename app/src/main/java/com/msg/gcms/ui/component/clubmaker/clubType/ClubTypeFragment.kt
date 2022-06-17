package com.msg.gcms.ui.component.clubmaker.clubType

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubTypeBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.MakeClubViewModel
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
                binding.majorBtn.id -> clubType = "major"
                binding.freeBtn.id -> clubType = "free"
                binding.personalBtn.id -> clubType = "personal"
            }
            makeClubViewModel.clubTypeChange(clubType)

            this.findNavController().navigate(R.id.action_clubTypeFragment_to_clubIntroduceFragment)
        } else if (view.id == binding.clubTypeBackBtn.id) {
            activity?.finish()
        }
    }
}
