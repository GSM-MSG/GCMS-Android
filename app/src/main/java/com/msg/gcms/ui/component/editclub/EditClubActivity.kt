package com.msg.gcms.ui.component.editclub

import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityEditClubBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.base.LottieFragment
import com.msg.viewmodel.EditClubViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditClubActivity : BaseActivity<ActivityEditClubBinding>(R.layout.activity_edit_club) {

    private val editClubViewModel by viewModels<EditClubViewModel>()
    private val lottie by lazy { LottieFragment() }

    override fun viewSetting() {
        binding.activity = this
        getClubInfo()
    }

    override fun observeEvent() {
        observeClubInfo()
    }

    private fun observeClubInfo() {
        editClubViewModel.clearClubInfo()
        editClubViewModel.clubInfo.observe(this) {
            if(it != null)
            setClubInfo()
            stopLottie()
        }
    }

    private fun setClubInfo() {
        with(binding) {
            bannerImageView.load(editClubViewModel.clubInfo.value!!.club.bannerUrl) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8f))
            }
            bannerIcon.visibility = View.GONE
            bannerTxt.visibility = View.GONE
            with(editClubViewModel.clubInfo.value!!.club) {
                clubNameEt.setText(title)
                clubDescriptionEt.setText(description)
                LinkEt.setText(notionLink)
                contactEt.setText(contact)
                teacherNameEt.setText(teacher)
            }
        }
    }

    private fun getClubInfo() {
        val query = intent.getStringExtra("query").toString().split(" + ")
        startLottie(this.supportFragmentManager)
        Log.d("TAG", "getClubInfo: $query ${query[0]}, ${query[1]}")
        editClubViewModel.getClubInfo(type = query[1], clubName = query[0])
    }

    fun buttonClickListener(view: View) {
        with(binding) {
            when (view.id) {
                backBtn.id -> finish()
                addActivityPhotoBtn.id -> intentGallery()
                completeBtn.id -> editClubInfo()
            }
        }
    }

    private fun intentGallery() {
    }

    private fun editTextInspector() {
        with(binding) {
            if (
                clubNameEt.text.isNotEmpty() &&
                clubDescriptionEt.text.isNotEmpty() &&
                LinkEt.text.isNotEmpty() &&
                contactEt.text.isNotEmpty()
            ) {
                linkInspector()
            } else {
                shortToast("필수 사항을 모두 입력해주세요")
            }
        }
    }

    private fun linkInspector() {
        if (binding.LinkEt.text.toString().startsWith("http://") || binding.LinkEt.text.toString()
                .startsWith("https://") && binding.LinkEt.text.toString().endsWith(".com")
        ) {
            postImage()
        } else {
            shortToast("URL 형식으로 주소를 입력해주세요")
        }
    }

    private fun postImage() {

    }

    private fun editClubInfo() {
        editTextInspector()
    }

    private fun startLottie(fragmentManager: FragmentManager) {
        if(!lottie.isAdded){
            lottie.show(fragmentManager,"Lottie")
        }
    }

    private fun stopLottie() {
        if (lottie.isAdded) {
            lottie.dismissAllowingStateLoss()
        }
    }
}