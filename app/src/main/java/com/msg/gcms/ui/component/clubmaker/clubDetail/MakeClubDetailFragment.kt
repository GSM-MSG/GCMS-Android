package com.msg.gcms.ui.component.clubmaker.clubDetail

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msg.gcms.R
import com.msg.gcms.data.local.entity.ActivityPhotoType
import com.msg.gcms.databinding.FragmentMakeClubDetailBinding
import com.msg.gcms.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeClubDetailFragment :
    BaseFragment<FragmentMakeClubDetailBinding>(R.layout.fragment_make_club_detail) {

    var list = mutableListOf<ActivityPhotoType>()
    private lateinit var adapter : ActivityPhotosAdapter

    override fun init() {
        binding.fragment = this
        settingRecyclerView()
    }

    private fun settingRecyclerView() {
        adapter = ActivityPhotosAdapter()
        with(binding.clubActivePicture) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
            setHasFixedSize(true)
        }
        with(list) {
            add(ActivityPhotoType(""))
        }
        adapter.items = list
        binding.clubActivePicture.adapter = adapter

    }

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val imageUrl = it.data?.data
            with(binding.addBannerPicture) {
                setImageURI(imageUrl)
                scaleType = ImageView.ScaleType.CENTER_CROP
                binding.imageView7.visibility = View.GONE
                binding.addImageTxt.visibility = View.GONE
            }
        }
    }

    fun clickedPageBtn(view: View) {
        when (view.id) {
            binding.clubTypeBackBtn.id -> {
                this.findNavController().popBackStack()
            }
            // binding.nextBtn.id -> {
            //     activity?.finish()
            // }
        }
    }

    fun galleryOpen(view: View) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            with(binding) {
                when (view.id) {
                    addBannerPicture.id -> {
                        val photoPickerIntent = Intent(Intent.ACTION_PICK)
                        photoPickerIntent.type = "image/*"
                        photoPickerIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        photoPickerIntent.action = Intent.ACTION_GET_CONTENT
                        getContent.launch(photoPickerIntent)
                    }
                    // clubActivePicture.id -> {
                    //     val activityPhotosIntent = Intent(Intent.ACTION_GET_CONTENT)
                    //     activityPhotosIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    //     activityPhotosIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    //     startActivityForResult(activityPhotosIntent, Activity.RESULT_OK)
                    // }
                    nextBtn.id -> {
                        val activityPhotosIntent = Intent(Intent.ACTION_PICK)
                        activityPhotosIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        activityPhotosIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        activityPhotosIntent.action = Intent.ACTION_GET_CONTENT
                        startActivityForResult(activityPhotosIntent, Activity.RESULT_OK)
                    }
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && resultCode == requestCode) {
            if (data?.clipData != null) {
                if(data.clipData!!.itemCount > 4) {
                    shortToast("활동사진은 최대 4개까지 가능합니다.")
                    return
                }
                else {
                    list.clear()
                    for (i in 0 until data.clipData!!.itemCount) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        list.add(ActivityPhotoType(activityPhoto = imageUri.toString()))
                    }
                    Log.d("TAG",list.toString())
                }
            }
        }
    }

}
