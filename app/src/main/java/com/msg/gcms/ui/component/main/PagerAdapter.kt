package com.msg.gcms.ui.component.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.msg.gcms.ui.component.club.ClubFragment

class PagerAdapter(fm: FragmentManager, lc: Lifecycle): FragmentStateAdapter(fm, lc) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ClubFragment()
            1 -> ClubFragment()
            2 -> ClubFragment()
            else -> error("no such position: $position")
        }
    }
}