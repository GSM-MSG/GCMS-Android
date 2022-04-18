package com.msg.gcms.ui.component.clubmaker

import androidx.activity.viewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityMakeClubBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.viewmodel.MakeClubViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeClubActivity : BaseActivity<ActivityMakeClubBinding>(R.layout.activity_make_club) {

    private val makeClubViewModel by viewModels<MakeClubViewModel>()

    override fun viewSetting() {
        binding.activity = this
    }


    override fun observeEvent() {
    }
}