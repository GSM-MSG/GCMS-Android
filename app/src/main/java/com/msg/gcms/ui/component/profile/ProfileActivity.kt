package com.msg.gcms.ui.component.profile

import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityProfileBinding
import com.msg.gcms.ui.base.BaseActivity

class ProfileActivity: BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {
    override fun observeEvent() {

    }

    override fun viewSetting() {
        clickBackBtn()
        initRecyclerview()
    }

    private fun clickBackBtn(){
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerview(){
        binding.myClubViewpager.apply {
            adapter = ProfileClubAdapter()
            clipToPadding = false
            setPadding(150,0,150,0)
            pageMargin = 50
        }
    }
}