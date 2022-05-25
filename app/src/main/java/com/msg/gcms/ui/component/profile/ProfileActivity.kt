package com.msg.gcms.ui.component.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import androidx.activity.viewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityProfileBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.intro.IntroActivity
import com.msg.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {
    private val viewModel by viewModels<ProfileViewModel>()
    override fun observeEvent() {
        isClub()
        isLogout()
    }

    override fun viewSetting() {
        getUserInfo()
        clickBackBtn()
        clickProfileEdit()
        clickLogout()
    }

    private fun isLogout(){
        viewModel.logoutStatus.observe(this){
            if(it) {
                val intent = Intent(this, IntroActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun isClub() {
        viewModel.clubStatus.observe(this) {
            if (it) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.profileFragmentView, ProfileClubFragment()).commit()
            }
        }
    }

    private fun getUserInfo() {
        viewModel.getUserInfo()
    }

    private fun clickBackBtn() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun clickProfileEdit() {
        binding.profileImg.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, 0)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
                )
            }
        }
    }

    private fun clickLogout(){
        binding.logoutBtn.setOnClickListener {
            viewModel.logout()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                try {
                    val imgIn = contentResolver.openInputStream(data!!.data!!)
                    val img = BitmapFactory.decodeStream(imgIn)
                    imgIn!!.close()
                    binding.profileImg.setImageBitmap(img)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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
