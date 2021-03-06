package com.msg.gcms.ui.component.clubmaker.clubIntroduce

import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubIntroduceBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.MakeClubViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubIntroduceFragment :
    BaseFragment<FragmentClubIntroduceBinding>(R.layout.fragment_club_introduce) {

    private val makeClubViewModel by activityViewModels<MakeClubViewModel>()

    override fun init() {
        binding.fragment = this
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    fun clickedButton(view: View) {
        when (view.id) {
            binding.backBtn.id -> this.findNavController().popBackStack()
            binding.nextBtn.id -> editTextCheck()
        }
    }

    fun editTextCheck() {
        if (binding.clubNameEt.text.isNotEmpty() && binding.clubIntroduceEt.text.isNotEmpty() && binding.contactEt.text.isNotEmpty() && binding.linkUrlEt.text.isNotEmpty() && binding.linkName.text.isNotEmpty()) {
            if(binding.linkUrlEt.text.toString().contains("https://")){
                with(makeClubViewModel) {
                    title = binding.clubNameEt.text.toString().trim()
                    description = binding.clubIntroduceEt.text.toString().trim()
                    contact = binding.contactEt.text.toString().trim()
                    notionLink = binding.linkUrlEt.text.toString().trim()
                    teacher = binding.teacherNameEt.text.toString().trim()
                }
                this.findNavController()
                    .navigate(R.id.action_clubIntroduceFragment_to_makeClubDetailFragment)
            } else {
                shortToast("노션 링크앞에 https://를 붙여주세요!!")
            }
        } else shortToast("필수 사항들을 모두 입력해주세요!!")
    }
}
