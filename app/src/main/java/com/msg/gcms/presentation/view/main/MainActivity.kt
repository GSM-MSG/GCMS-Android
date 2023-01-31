package com.msg.gcms.presentation.view.main

import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.msg.gcms.R
import com.msg.gcms.data.remote.dto.club.response.ClubInfoResponse
import com.msg.gcms.databinding.ActivityMainBinding
import com.msg.gcms.presentation.base.BaseActivity
import com.msg.gcms.presentation.view.club.detail.DetailFragment
import com.msg.gcms.presentation.viewmodel.ClubDetailViewModel
import com.msg.gcms.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by viewModels<MainViewModel>()
    private val detailViewModel by viewModels<ClubDetailViewModel>()
    private var backButtonWait: Long = 0

    override fun viewSetting() {
        initBottomNav()
    }

    override fun observeEvent() {
        observeBottomNav()
        observeSetNav()
    }

    override fun onResume() {
        super.onResume()
        if (intent.getBooleanExtra("isProfile", false)) {
            detailViewModel.setResult(intent.getSerializableExtra("result") as ClubInfoResponse)
            detailViewModel.setIsProfile(true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_club, DetailFragment())
                .commit()
        }
    }

    private fun observeSetNav() {
        detailViewModel.showNav.observe(this) {
            binding.bottomNavigation.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun observeBottomNav() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            var fragNum = 0
            fragNum = when (it.itemId) {
                R.id.majorFragment -> 0
                R.id.freeFragment -> 1
                R.id.personalFragment -> 2
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
        supportFragmentManager.fragments.filter { it is OnBackPressedListener }
            .map { it as OnBackPressedListener }
            .forEach { it.onBackPressed(); return }

        if (System.currentTimeMillis() - backButtonWait >= 2000) {
            backButtonWait = System.currentTimeMillis()
            longToast("뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.")
        } else {
            super.onBackPressed()
            finish()
        }
    }

    interface OnBackPressedListener {
        fun onBackPressed()
    }
}