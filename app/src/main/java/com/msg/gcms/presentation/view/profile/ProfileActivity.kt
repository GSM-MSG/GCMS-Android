package com.msg.gcms.presentation.view.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.snackbar.Snackbar
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityProfileBinding
import com.msg.gcms.presentation.base.BaseActivity
import com.msg.gcms.presentation.base.BaseModal
import com.msg.gcms.presentation.utils.enterActivity
import com.msg.gcms.presentation.utils.exitActivity
import com.msg.gcms.presentation.utils.start
import com.msg.gcms.presentation.utils.stop
import com.msg.gcms.presentation.utils.toFile
import com.msg.gcms.presentation.utils.toMultiPartBody
import com.msg.gcms.presentation.view.intro.IntroActivity
import com.msg.gcms.presentation.view.withdrawal.WithdrawalActivity
import com.msg.gcms.presentation.view.withdrawal.WithdrawalDialog
import com.msg.gcms.presentation.viewmodel.AuthViewModel
import com.msg.gcms.presentation.viewmodel.ProfileViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            if (imageUri != null) {
                val file = imageUri.toFile(this)
                profileViewModel.uploadImg(file.toMultiPartBody())
            }
        }

    override fun observeEvent() {
        myProfile()
        isClub()
        observeProfileInfo()
        observeEditProfile()
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.getUserInfo()
    }

    override fun viewSetting() {
        clickBackBtn()
        clickProfileEdit()
        clickLogout()
    }

    private fun myProfile() {
        profileViewModel.profileData.observe(this) {
            binding.apply {
                userNameTxt.text = it.name
                userClassTxt.text =
                    "${it.grade}학년 ${it.classNum}반 ${it.number}번"
                profileImg.load(it.profileImg ?: R.drawable.ic_default_profile) {
                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    private fun observeProfileInfo() {
        profileViewModel.getUserInfo.observe(this) {
            when (it) {
                Event.Success -> {
                    binding.profileLoadingView.stop()
                }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다, 로그아웃 이후 다시 로그인해주세요.",
                        this
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(this, IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.NotFound -> {
                    BaseModal("오류", "사용자를 찾을 수 없습니다.", this).show()
                }
                Event.UnKnown -> {
                    BaseModal("오류", "알수 없는 오류 발생, 개발자에게 문의해주세요", this).show()
                }
            }
        }
    }

    private fun observeEditProfile() {
        profileViewModel.editUserInfo.observe(this) {
            when (it) {
                Event.Success -> {
                    profileViewModel.getUserInfo()
                }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다, 로그아웃 이후 다시 로그인해주세요.",
                        this
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(this, IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                else -> {
                    BaseModal("오류", "프로필 수정에 실패했습니다, 다시 시도해주세요.", this).show()
                }
            }
        }
    }

    private fun isClub() {
        profileViewModel.clubStatus.observe(this) {
            if (it) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.profileList, ProfileClubFragment()).commit()
            }
        }
    }

    private fun clickBackBtn() {
        binding.backBtn.setOnClickListener {
            exitActivity(this)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        exitActivity(this)
    }

    private fun clickProfileEdit() {
        binding.profileImg.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getContent.launch("image/*")
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
                )
            }
        }
    }

    private fun clickLogout() {
        binding.logoutOption.setOnClickListener {
            WithdrawalDialog(this).apply {
                setDialogListener(object : WithdrawalDialog.WithdrawalDialogListener {
                    override fun logout() {
                        authViewModel.logoutRequest()
                        enterActivity(this@ProfileActivity, IntroActivity())
                    }

                    override fun goWithdrawal() {
                        val intent = Intent(this@ProfileActivity, WithdrawalActivity::class.java)
                        startActivity(intent)
                    }
                })
                show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(binding.root, "갤러리 접근 권한에 동의되었습니다.", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(binding.root, "권한에 동의하지 않을 경우 이용할 수 없습니다.", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
