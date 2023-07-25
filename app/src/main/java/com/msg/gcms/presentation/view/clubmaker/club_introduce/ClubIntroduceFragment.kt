package com.msg.gcms.presentation.view.clubmaker.club_introduce

import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubIntroduceBinding
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.utils.keyboardHide
import com.msg.gcms.presentation.viewmodel.MakeClubViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubIntroduceFragment :
    BaseFragment<FragmentClubIntroduceBinding>(R.layout.fragment_club_introduce) {

    private val makeClubViewModel by activityViewModels<MakeClubViewModel>()

    override fun observeEvent() = Unit

    override fun initView() {
        binding.fragment = this
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        binding.clubIntroduceLayout.setOnClickListener {
            keyboardHide(
                requireActivity(),
                binding.clubNameEt,
                binding.clubIntroduceEt,
                binding.linkName,
                binding.teacherNameEt,
                binding.contactEt
            )
        }
    }

    fun clickedButton(view: View) {
        when (view.id) {
            binding.backBtn.id -> this.findNavController().popBackStack()
            binding.nextBtn.id -> editTextCheck()
        }
    }

    private fun editTextCheck() {
        if (binding.clubNameEt.text.isNotEmpty() && binding.clubIntroduceEt.text.isNotEmpty() && binding.contactEt.text.isNotEmpty() && binding.linkName.text.isNotEmpty()) {
            if (binding.linkName.text.startsWith("http://") || binding.linkName.text.toString()
                    .startsWith("https://")
            ) {
                with(makeClubViewModel) {
                    title = binding.clubNameEt.text.toString().trim()
                    description = binding.clubIntroduceEt.text.toString().trim()
                    contact = binding.contactEt.text.toString().trim()
                    notionLink = binding.linkName.text.toString().trim()
                    teacher = binding.teacherNameEt.text.toString().trim()
                }
                this.findNavController()
                    .navigate(R.id.action_clubIntroduceFragment_to_makeClubDetailFragment)
            } else {
                shortToast("링크 형식으로 입력해주세요!!")
            }
        } else shortToast("필수 사항들을 모두 입력해주세요!!")
    }
}
