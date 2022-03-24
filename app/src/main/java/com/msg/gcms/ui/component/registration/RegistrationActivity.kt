package com.msg.gcms.ui.component.registration

import androidx.activity.viewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityRegistrationBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.viewmodel.RegistrationViewModel

class RegistrationActivity :
    BaseActivity<ActivityRegistrationBinding>(R.layout.activity_registration) {

    private val registrationViewModel by viewModels<RegistrationViewModel>()


    override fun observeEvent() {
    }


    override fun viewSetting() {

    }

}