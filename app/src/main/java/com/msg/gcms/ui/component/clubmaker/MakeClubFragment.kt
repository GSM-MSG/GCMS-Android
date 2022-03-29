package com.msg.gcms.ui.component.clubmaker

import android.view.View
import androidx.fragment.app.activityViewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentMakeClubBinding
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.MainViewModel

class MakeClubFragment : BaseFragment<FragmentMakeClubBinding>(R.layout.fragment_make_club),
    View.OnClickListener {

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun init() {

    }


    override fun onStart() {
        binding.apply {
            majorBtn.setOnClickListener(this@MakeClubFragment)
            freeBtn.setOnClickListener(this@MakeClubFragment)
            personalBtn.setOnClickListener(this@MakeClubFragment)
        }
        super.onStart()
    }

    override fun onClick(v: View?) {
        binding.majorBtn.isChecked = binding.majorBtn.id == v!!.id
        binding.freeBtn.isChecked = binding.freeBtn.id == v.id
        binding.personalBtn.isChecked = binding.personalBtn.id == v.id
    }

}