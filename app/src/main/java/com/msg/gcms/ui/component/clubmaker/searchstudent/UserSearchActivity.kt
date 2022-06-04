package com.msg.gcms.ui.component.clubmaker.searchstudent

import android.view.View
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityUserSearchBinding
import com.msg.gcms.ui.base.BaseActivity

class UserSearchActivity : BaseActivity<ActivityUserSearchBinding>(R.layout.activity_user_search) {
    override fun viewSetting() {
        binding.activity = this
    }

    override fun observeEvent() {

    }

    fun onClickListener(view : View) {
        when(view.id) {
            binding.goBackBtn.id -> {
                finish()
            }
            binding.selectBtn.id -> {
                finish()
            }
        }
    }
}