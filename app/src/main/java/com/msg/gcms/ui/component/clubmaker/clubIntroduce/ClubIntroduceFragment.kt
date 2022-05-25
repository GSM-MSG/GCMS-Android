package com.msg.gcms.ui.component.clubmaker.clubIntroduce

import android.view.View
import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubIntroduceBinding
import com.msg.gcms.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubIntroduceFragment :
    BaseFragment<FragmentClubIntroduceBinding>(R.layout.fragment_club_introduce) {
    override fun init() {
        binding.fragment = this
    }

    fun clickedButton(view: View) {
        when (view.id) {
            binding.backBtn.id -> this.findNavController().popBackStack()
            binding.nextBtn.id -> editTextCheck()
        }
    }

    fun editTextCheck() {
        if (binding.clubNameEt.text.isNotEmpty() && binding.clubIntroduceEt.text.isNotEmpty() && binding.contactEt.text.isNotEmpty() && binding.linkUrlEt.text.isNotEmpty() && binding.linkName.text.isNotEmpty()) {
            this.findNavController()
                .navigate(R.id.action_clubIntroduceFragment_to_makeClubDetailFragment)
        } else shortToast("필수 사항들을 모두 입력해주세요!!")
    }
}
