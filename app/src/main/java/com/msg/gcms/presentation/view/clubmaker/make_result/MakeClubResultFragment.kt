package com.msg.gcms.presentation.view.clubmaker.make_result

import android.view.View
import androidx.fragment.app.activityViewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentMakeClubResultBinding
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.utils.enterActivity
import com.msg.gcms.presentation.view.main.MainActivity
import com.msg.gcms.presentation.viewmodel.MakeClubViewModel
import com.msg.gcms.presentation.viewmodel.util.Event

class MakeClubResultFragment :
    BaseFragment<FragmentMakeClubResultBinding>(R.layout.fragment_make_club_result) {

    private val makeClubViewModel by activityViewModels<MakeClubViewModel>()

    override fun init() {
        binding.fragment = this
        val result = makeClubViewModel.createClubResult.value!!
        showCreateClubResult(result)
    }

    private fun showCreateClubResult(result: Event) {
        val state: Int
        val message: Int
        val icon: Int

        when (result) {
            Event.Success -> {
                state = R.string.create_club_success_state
                message = R.string.create_club_success_message
                icon = R.drawable.ic_email
            }
            Event.ForBidden -> {
                state = R.string.create_club_error_state
                message = R.string.create_club_forbidden_message
                icon = R.drawable.ic_error
            }
            Event.Server -> {
                state = R.string.create_club_server_error_state
                message = R.string.create_club_server_message
                icon = R.drawable.ic_server_error
            }
            else -> {
                state = R.string.create_club_unknown_error_state
                message = R.string.create_club_unknown_message
                icon = R.drawable.ic_unknown
            }
        }
        showResultState(state = state, message = message, icon = icon)
    }

    private fun showResultState(state: Int, message: Int, icon: Int) {
        binding.statusTv.setText(state)
        binding.statusMessageTv.setText(message)
        binding.stateImg.setImageResource(icon)
    }

    fun onClickExitBtn(view: View) {
        when (view.id) {
            binding.checkBtn.id, binding.exitBtn.id -> {
                enterActivity(requireActivity(), MainActivity())
                requireActivity().finish()
            }
        }
    }
}