package com.msg.gcms.presentation.view.clubmaker.club_type

import android.content.Context
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubTypeBinding
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.utils.exitActivity
import com.msg.gcms.presentation.viewmodel.MakeClubViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubTypeFragment : BaseFragment<FragmentClubTypeBinding>(R.layout.fragment_club_type) {

    private val makeClubViewModel by activityViewModels<MakeClubViewModel>()
    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitActivity(requireActivity())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun observeEvent() = Unit

    override fun initView() {
        binding.fragment = this
    }

    private lateinit var clubType: String

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
            exitActivity(requireActivity())
        }
    }
}
