package com.msg.gcms.ui.component.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityProfileBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.component.intro.IntroActivity
import com.msg.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {
    private val viewModel by viewModels<ProfileViewModel>()
    private lateinit var client: GoogleSignInClient
    override fun observeEvent() {
        myProfile()
        isClub()
    }

    override fun viewSetting() {
        clickBackBtn()
        clickProfileEdit()
        clickLogout()
    }

    private fun myProfile(){
        viewModel.getUserInfo()
        viewModel.profileData.observe(this) {
            binding.apply {
                userNameTxt.text = it.userData.name
                userClassTxt.text =
                    "${it.userData.grade}학년 ${it.userData.`class`}반 ${it.userData.num}번"
                profileImg.load(it.userData.userImg) {
                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    private fun isLogout() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(this, gso)
        client.signOut()
        viewModel.logoutStatus.observe(this) {
            if (it) {
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
                    .replace(R.id.profileList, ProfileClubFragment()).commit()
            }
        }
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
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
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
            isLogout()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                try {
                    binding.profileImg.load(data?.data) {
                        transformations(CircleCropTransformation())
                    }
                    val currentUri = data?.data
                    val file = File(getPathFromUri(currentUri))
                    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                    val img = MultipartBody.Part.createFormData("files", file.name, requestFile)
                    viewModel.uploadImg(img)
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

    @SuppressLint("Range")
    private fun getPathFromUri(uri: Uri?): String? {
        val cursor: Cursor? = contentResolver.query(uri!!, null, null, null, null)
        cursor?.moveToNext()
        val path: String = cursor!!.getString(cursor!!.getColumnIndex("_data"))
        cursor.close()
        return path
    }
}
