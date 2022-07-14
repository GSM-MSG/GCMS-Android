package com.msg.gcms.ui.component.editclub

import android.view.View
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityEditClubBinding
import com.msg.gcms.ui.base.BaseActivity

class EditClubActivity : BaseActivity<ActivityEditClubBinding>(R.layout.activity_edit_club) {
    override fun viewSetting() {
        binding.activity = this
    }

    override fun observeEvent() {
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
}