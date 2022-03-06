package com.msg.gcms.ui.component.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.msg.gcms.ui.component.free.FreeFragment
import com.msg.gcms.ui.component.major.MajorFragment
import com.msg.gcms.ui.component.personal.PersonalFragment

class PagerAdapter(fm: FragmentManager, lc: Lifecycle): FragmentStateAdapter(fm, lc) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MajorFragment()
            1 -> FreeFragment()
            2 -> PersonalFragment()
            else -> error("no such position: $position")
        }
    }
}