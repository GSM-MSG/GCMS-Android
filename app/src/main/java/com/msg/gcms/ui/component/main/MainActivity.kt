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
        binding.fragmentView.apply {
            adapter = PagerAdapter(supportFragmentManager, lifecycle)
            registerOnPageChangeCallback(PageChangeCallback(binding.bottomNavigation))
        }
        binding.bottomNavigation.setOnNavigationItemSelectedListener { navSelected(it) }
    }

    private fun navSelected(item: MenuItem): Boolean {
        val checked = item.setChecked(true)
        mainViewModel.setClubName(binding.fragmentView.currentItem)
        when (checked.itemId) {
            R.id.majorFragment -> {
                binding.fragmentView.currentItem = 0
            }
            R.id.freeFragment -> {
                binding.fragmentView.currentItem = 1
            }
            R.id.personalFragment -> {
                binding.fragmentView.currentItem = 2
            }
        }
        return false
    }
}