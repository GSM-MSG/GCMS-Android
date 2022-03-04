package com.msg.gcms.ui.component.main

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.msg.gcms.R

class PageChangeCallback(val nav_view: BottomNavigationView): ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        nav_view.selectedItemId = when (position) {
            0 -> R.id.majorFragment
            1 -> R.id.freeFragment
            2 -> R.id.personalFragment
            else -> error("no such position: $position")
        }
    }
}