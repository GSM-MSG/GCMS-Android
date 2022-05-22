package com.msg.gcms.ui.component.clubmaker.clubDetail

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentMakeClubDetailBinding
import com.msg.gcms.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeClubDetailFragment :
    BaseFragment<FragmentMakeClubDetailBinding>(R.layout.fragment_make_club_detail) {

    override fun init() {
        binding.fragment = this
    }

    val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            var imageUrl = it.data?.data
            with(binding.addBannerPicture) {
                setImageURI(imageUrl)
                scaleType = ImageView.ScaleType.CENTER_CROP
                binding.imageView7.visibility = View.GONE
                binding.addImageTxt.visibility = View.GONE
            }
        }
    }

    val getContents = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {

        }
    }

    fun clickedNextBtn(view: View) {
        activity?.finish()
    }


    fun galleryOpen(view: View) {
        with(binding) {
            when (view.id) {
                addBannerPicture.id -> {
                    val photoPickerIntent = Intent(Intent.ACTION_PICK)
                    photoPickerIntent.type = "image/*"
                    photoPickerIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    photoPickerIntent.action = Intent.ACTION_GET_CONTENT
                    getContent.launch(photoPickerIntent)
                }
                clubActivePicture.id -> {
                    val activityPhotos = Intent(Intent.ACTION_PICK)
                    activityPhotos.type = "image/*"
                    activityPhotos.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    activityPhotos.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    getContents.launch(activityPhotos)
                }
            }
        }
    }
}
