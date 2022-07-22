package com.msg.gcms.ui.component.editclub

import android.util.Log
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityEditClubBinding
import com.msg.gcms.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditClubActivity: BaseActivity<ActivityEditClubBinding>(R.layout.activity_edit_club) {

    override fun viewSetting() {
        binding.activity = this
    }

    override fun observeEvent() {
        getClubType()
    }

    private fun getClubType() {
        Log.d("TAG", "getClubType: ${intent.getStringExtra("type")}")
    }
}