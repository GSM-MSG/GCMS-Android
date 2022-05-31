package com.msg.gcms.ui.component.profile

import androidx.fragment.app.activityViewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentProfileClubBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.ProfileViewModel

class ProfileClubFragment: BaseFragment<FragmentProfileClubBinding>(R.layout.fragment_profile_club) {
    private val viewModel by activityViewModels<ProfileViewModel>()
    override fun init() {
        isAfterSchool()
    }

    private fun isAfterSchool() {
        viewModel.afterSchoolStatus.observe(this){
            if(it){
                childFragmentManager.beginTransaction().replace(R.id.afterSchoolFragmentView, ProfileAfterSchoolFragment()).commit()
            }
        }
    }
}