package com.msg.gcms.ui.component.editclub

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentEditClubBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.EditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditClubFragment: BaseFragment<FragmentEditClubBinding>(R.layout.fragment_edit_club) {

    private val editViewModel by activityViewModels<EditViewModel>()

    override fun init() {
        binding.fragment = this
        observeEvent()
    }

    fun buttonClickListener(view: View) {
        when(view.id) {
            binding.backBtn.id -> clickBackBtn()
            binding.addActivityPhotoBtn.id -> addActivityPhoto()
            binding.completeBtn.id -> editClub()
        }
    }

    private fun observeEvent() {
        observeClubInfo()
        observeClubTypeDivider()
    }

    private fun observeClubTypeDivider(){
        editViewModel.clubName.observe(this) {
            getClubInfo()
        }
    }

    private fun observeClubInfo() {
        editViewModel.clubInfo.observe(this) {
            if(it != null) {
                with(binding){
                    clubNameEt.setText(it.club.title)
                    clubDescriptionEt.setText(it.club.description)
                    LinkEt.setText(it.club.notionLink)
                    contactEt.setText(it.club.contact)
                    teacherNameEt.setText(it.club.teacher)
                }
            }
        }
    }

    private fun getClubInfo() {
        Log.d("TAG", "getClubInfo")
        editViewModel.getClubInfo()
    }

    private fun clickBackBtn() {

    }

    private fun addActivityPhoto() {

    }

    private fun editClub() {

    }
}