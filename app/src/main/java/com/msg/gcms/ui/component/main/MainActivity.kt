package com.msg.gcms.ui.component.main

import android.view.MenuItem
import androidx.activity.viewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityMainBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by viewModels<MainViewModel> ()

    override fun viewSetting() {
        initBottomNav()
    }

    override fun observeEvent() {
        mainViewModel.clubName.observe(this, {
            binding.clubNameTxt.text = it
        })
    }

    private fun initBottomNav() {
        binding.fragmentClub.apply {
            adapter = PagerAdapter(supportFragmentManager, lifecycle)
            registerOnPageChangeCallback(PageChangeCallback(binding.bottomNavigation))
        }
        binding.bottomNavigation.setOnNavigationItemSelectedListener { navSelected(it) }
    }

    private fun navSelected(item: MenuItem): Boolean {
        val checked = item.setChecked(true)
        mainViewModel.setClubName(binding.fragmentClub.currentItem)
        when (checked.itemId) {
            R.id.majorFragment -> {
                binding.fragmentClub.currentItem = 0
            }
            R.id.freeFragment -> {
                binding.fragmentClub.currentItem = 1
            }
            R.id.personalFragment -> {
                binding.fragmentClub.currentItem = 2
            }
        }
        return false
    }
}