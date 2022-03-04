package com.msg.gcms.ui.component.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityMainBinding
import com.msg.gcms.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun viewSetting() {
        initBottomNav()
    }

    override fun observeEvent() {

    }

    private fun initBottomNav() {
        binding.fragmentView.apply {
            adapter = PagerAdapter(supportFragmentManager, lifecycle)
            registerOnPageChangeCallback(PageChangeCallback(binding.bottomNavigation))
        }
        binding.bottomNavigation.setOnNavigationItemSelectedListener { navSelected(it) }
    }

    private fun navSelected(item: MenuItem): Boolean{
        val checked = item.setChecked(true)
        when(checked.itemId){
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