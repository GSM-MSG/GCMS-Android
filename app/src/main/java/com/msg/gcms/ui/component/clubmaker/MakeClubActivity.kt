package com.msg.gcms.ui.component.clubmaker

import android.content.pm.PackageManager
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityMakeClubBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.viewmodel.MakeClubViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeClubActivity : BaseActivity<ActivityMakeClubBinding>(R.layout.activity_make_club) {

    private val makeClubViewModel by viewModels<MakeClubViewModel>()

    override fun viewSetting() {
        binding.activity = this
    }

    override fun observeEvent() {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1 -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(binding.root, "갤러리 접근 권한에 동의되었습니다.", Snackbar.LENGTH_SHORT).show()
                }else {
                    Snackbar.make(binding.root, "권한에 동의하지 않을 경우 이용할 수 없습니다.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}
