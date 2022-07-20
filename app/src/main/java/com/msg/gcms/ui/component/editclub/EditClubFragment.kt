package com.msg.gcms.ui.component.editclub

import android.view.View
import androidx.fragment.app.activityViewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentEditClubBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.ClubDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditClubFragment: BaseFragment<FragmentEditClubBinding>(R.layout.fragment_edit_club) {

    private val clubDetailViewModel by activityViewModels<ClubDetailViewModel>()

    override fun init() {
        binding.fragment = this
    }

    fun buttonClickListener(view: View) {
        when(view.id) {
            binding.backBtn.id -> clickBackBtn()
            binding.addActivityPhotoBtn.id -> addActivityPhoto()
            binding.completeBtn.id -> editClub()
        }
    }

    private fun clickBackBtn() {

    }

    private fun addActivityPhoto() {

    }

    private fun editClub() {

    }
}