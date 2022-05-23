package com.msg.gcms.ui.component.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.viewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityProfileBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {
    private val viewModel by viewModels<ProfileViewModel>()
    override fun observeEvent() {
        viewModel.clubStatus.observe(this) {
            if (it) {
                isClub()
            }
        }
    }

    override fun viewSetting() {
        getUserInfo()
        clickBackBtn()
        clickProfileEdit()
    }

    private fun getUserInfo(){
        viewModel.getUserInfo()
    }

    private fun clickBackBtn() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun clickProfileEdit() {
        binding.profileImg.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 0)
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

    private fun isClub(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.profileFragmentView, ProfileClubFragment()).commit()
    }
}
