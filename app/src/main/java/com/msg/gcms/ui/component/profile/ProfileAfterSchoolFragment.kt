package com.msg.gcms.ui.component.profile

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentProfileAfterSchoolBinding
import com.msg.gcms.ui.adapter.AfterSchoolListAdapter
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.ProfileViewModel

class ProfileAfterSchoolFragment: BaseFragment<FragmentProfileAfterSchoolBinding>(R.layout.fragment_profile_after_school) {
    private val viewModel by activityViewModels<ProfileViewModel>()
    override fun init() {
        settingRecyclerView()
    }

    private fun settingRecyclerView() {
        binding.afterSchoolRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.afterSchoolRecyclerView.adapter = AfterSchoolListAdapter(viewModel.profileData.value!!.afterSchools)
    }
}