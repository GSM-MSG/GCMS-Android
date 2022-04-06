package com.msg.gcms.ui.component.main

import android.content.Intent
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityMainBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.profile.ProfileActivity
import com.msg.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by viewModels<MainViewModel>()
    private var backButtonWait : Long = 0

    override fun viewSetting() {
        initBottomNav()
        clickProfile()
    }

    override fun observeEvent() {
        mainViewModel.clubName.observe(this) {
            binding.clubNameTxt.text = it
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

    private fun clickProfile() {
        binding.profileBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (System.currentTimeMillis() - backButtonWait >= 2000) {
            backButtonWait = System.currentTimeMillis()
            shortToast("뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.")
        } else {
            finish()
        }
    }
}