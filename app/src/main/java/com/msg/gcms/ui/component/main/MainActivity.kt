package com.msg.gcms.ui.component.main

import android.content.Intent
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityMainBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.afterschool.JoinAfterSchoolActivity
import com.msg.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by viewModels<MainViewModel>()
    private var backButtonWait: Long = 0

    override fun viewSetting() {
        initBottomNav()
    }

    override fun observeEvent() {
        observeBottomNav()
        observeClickBtn()
    }

    private fun observeClickBtn() {
        binding.goAfterSchoolBtn.setOnClickListener {
            val joinIntent = Intent(this, JoinAfterSchoolActivity::class.java)
            startActivity(joinIntent)
        }
    }

    private fun observeBottomNav(){
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            var fragNum = 0
            when (it.itemId) {
                R.id.majorFragment -> fragNum = 0
                R.id.freeFragment -> fragNum = 1
                R.id.personalFragment -> fragNum = 2
                else -> return@setOnNavigationItemSelectedListener false
            }
            mainViewModel.setClubName(fragNum)
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun initBottomNav() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.fragment_club)?.findNavController()
        val nav = binding.bottomNavigation
        navController?.let {
            nav.setupWithNavController(navController)
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backButtonWait >= 2000) {
            backButtonWait = System.currentTimeMillis()
            longToast("뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.")
        } else {
            super.onBackPressed()
            finish()
        }
    }
}
