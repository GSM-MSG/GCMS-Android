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
        val result = makeClubViewModel.createClubResult.value!!
        showCreateClubResult(result)
    }

    private fun showCreateClubResult(result: Event) {
        lateinit var state: String
        lateinit var message: String
        var icon = R.drawable.ic_email

        when (result) {
            Event.Success -> {
                state = R.string.create_club_success_state.toString()
                message = R.string.create_club_success_message.toString()
            }
            Event.ForBidden -> {
                state = R.string.create_club_error_state.toString()
                message = R.string.create_club_forbidden_message.toString()
                icon = R.drawable.ic_error
            }
            Event.Server -> {
                state = R.string.create_club_server_error_state.toString()
                message = R.string.create_club_server_message.toString()
                icon = R.drawable.ic_server_error
            }
            else -> {
                state = R.string.create_club_unknown_error_state.toString()
                message = R.string.create_club_unknown_message.toString()
                icon = R.drawable.ic_unknown
            }
        }
        showResultState(state = state, message = message, icon = icon)
    }

    private fun showResultState(state: String, message: String, icon: Int) {
        binding.statusTv.text = state
        binding.statusMessageTv.text = message
        binding.stateImg.setImageResource(icon)
    }

    fun onClickExitBtn(view: View) {
        when (view.id) {
            binding.checkBtn.id, binding.exitBtn.id -> {
                enterActivity(requireActivity(), MainActivity())
            }
        }
    }
}