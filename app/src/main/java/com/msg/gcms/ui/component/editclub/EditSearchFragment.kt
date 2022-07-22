package com.msg.gcms.ui.component.editclub

import android.view.View
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentEditSearchBinding
import com.msg.gcms.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditSearchFragment: BaseFragment<FragmentEditSearchBinding>(R.layout.fragment_edit_search) {

    override fun init() {
        binding.fragment = this
    }

    fun onClickListener(view: View) {
        when(view.id) {
            binding.goBackBtn.id -> {}
            binding.selectBtn.id -> {}
        }
    }
}