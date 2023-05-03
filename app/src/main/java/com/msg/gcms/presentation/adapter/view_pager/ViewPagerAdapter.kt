package com.msg.gcms.presentation.adapter.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.msg.gcms.presentation.view.club.FreeFragment
import com.msg.gcms.presentation.view.club.MajorFragment
import com.msg.gcms.presentation.view.club.PersonalFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private val fragments = listOf<Fragment>(MajorFragment(), FreeFragment(), PersonalFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}