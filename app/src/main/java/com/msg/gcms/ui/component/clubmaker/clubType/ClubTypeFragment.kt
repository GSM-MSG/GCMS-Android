package com.msg.gcms.ui.component.clubmaker.clubType

import android.util.Log
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
        observeClubType()
    }

    private fun observeClubType() {
        makeClubViewModel.clubType.observe(this) {
            this.findNavController().navigate(R.id.action_clubTypeFragment_to_clubIntroduceFragment)
        }
    }

    fun whenClickedBtn(view: View) {
        when (view.id) {
            binding.majorBtn.id -> {
                makeClubViewModel.clubTypeChange("major")
                Log.d("TAG", view.toString())
            }
            binding.freeBtn.id -> {
                makeClubViewModel.clubTypeChange("free")
                Log.d("TAG", view.toString())
            }
            binding.personalBtn.id -> {
                makeClubViewModel.clubTypeChange("personal")
                Log.d("TAG", view.toString())
            }
        }
    }
}