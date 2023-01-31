package com.msg.gcms.presentation.component.editclub

import androidx.activity.viewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityEditClubBinding
import com.msg.gcms.presentation.base.BaseActivity
import com.msg.gcms.presentation.viewmodel.EditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditClubActivity: BaseActivity<ActivityEditClubBinding>(R.layout.activity_edit_club) {

    private val editViewModel by viewModels<EditViewModel>()

    override fun viewSetting() {
        binding.activity = this
        getClubType()
    }

    override fun observeEvent() {
    }

    private fun getClubType() {
        val clubType = intent.getStringExtra("query")
        editViewModel.clubTypeDivider(clubType = clubType!!)
    }
}