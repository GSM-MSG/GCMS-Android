package com.msg.gcms.presentation.view.main

import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityMainBinding
import com.msg.gcms.presentation.base.BaseActivity
import com.msg.gcms.presentation.utils.enterActivity
import com.msg.gcms.presentation.view.club.detail.DetailFragment
import com.msg.gcms.presentation.view.clubmaker.MakeClubActivity
import com.msg.gcms.presentation.view.profile.ProfileActivity
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
        getProfileImage()
        clickProfile()
        clickMakeClubBtn()
    }

    override fun observeEvent() {
        observeBottomNav()
        observeSetNav()
        getProfileImage()
        observeProfileImage()
        observeClubName()
        observeProfileImage()
    }

    private fun getProfileImage() {
        mainViewModel.getProfileImageFromServer()
    }

    private fun observeProfileImage() {
        mainViewModel.getProfile.observe(this) {
            Log.d("TAG", "observeProfileImage: $it")
            if (it != null) binding.profileBtn.load(mainViewModel.getProfile())
            else binding.profileBtn.setImageResource(R.drawable.ic_profile)
        }
    }

    private fun clickProfile() {
        binding.profileBtn.setOnClickListener {
            enterActivity(this, ProfileActivity())
        }
    }

    private fun clickMakeClubBtn() {
        binding.addClubBtn.setOnClickListener {
            enterActivity(this, MakeClubActivity())
        }
    }

    private fun observeClubName() {
        mainViewModel.clubName.observe(this) {
            if (binding.clubNameTxt.text != mainViewModel.clubName.value) {
                mainViewModel.getClubList()
            }
            binding.clubNameTxt.text = mainViewModel.clubName.value
        }
    }

    override fun onResume() {
        super.onResume()
        if (intent.getBooleanExtra(
                "isProfile",
                false
            ) && detailViewModel.isProfile.value == false
        ) {
            detailViewModel.getDetail(intent.getLongExtra("clubId", 0))
            // detailViewModel.setResult(intent.getSerializableExtra("result") as ClubDetailData)
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
            val fragNum = when (it.itemId) {
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
        if (onBackPressedDispatcher.hasEnabledCallbacks()) {
            onBackPressedDispatcher.onBackPressed()
            return
        }

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
