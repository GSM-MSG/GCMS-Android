package com.msg.gcms.ui.component.clubmaker.clubDetail

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentMakeClubDetailBinding
import com.msg.gcms.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeClubDetailFragment :
    BaseFragment<FragmentMakeClubDetailBinding>(R.layout.fragment_make_club_detail) {

    val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            var imageUrl = it.data?.data
        }
    }

    override fun init() {
        binding.fragment = this
    }

    fun clickedNextBtn(view: View) {
        activity?.finish()
    }

    fun galleryOpen(view: View) {
        with(binding) {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            photoPickerIntent.action = Intent.ACTION_GET_CONTENT
            getContent.launch(photoPickerIntent)
            when (view.id) {
                addBannerPicture.id -> {

                }
                clubActivePicture.id -> {

                }
            }
        }
    }
}
