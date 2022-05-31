package com.msg.gcms.ui.component.clubmaker.clubDetail

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.msg.gcms.R
import com.msg.gcms.data.local.entity.ActivityPhotoType
import com.msg.gcms.databinding.FragmentMakeClubDetailBinding
import com.msg.gcms.ui.adapter.ActivityPhotosAdapter
import com.msg.gcms.ui.base.BaseFragment
import com.msg.gcms.utils.ItemDecorator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeClubDetailFragment :
    BaseFragment<FragmentMakeClubDetailBinding>(R.layout.fragment_make_club_detail) {

    var list = mutableListOf<ActivityPhotoType>()
    private lateinit var adapter: ActivityPhotosAdapter

    override fun init() {
        binding.fragment = this
        settingRecyclerView()
    }

    private fun settingRecyclerView() {
        with(binding.clubActivePicture) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(10))
        }
        val resource : Uri = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.ic_activity_photo)
        )
        Log.d("TAG","$resource")
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val imageUrl = it.data?.data
                with(binding.addBannerPicture) {
                    setImageURI(imageUrl)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    binding.imageView7.visibility = View.GONE
                    binding.addImageTxt.visibility = View.GONE
                }
                binding.addBannerPicture.load(imageUrl)
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
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            with(binding) {
                when (view.id) {
                    addBannerPicture.id -> {
                        val photoPickerIntent = Intent(Intent.ACTION_PICK)
                        photoPickerIntent.type = "image/*"
                        photoPickerIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        photoPickerIntent.action = Intent.ACTION_GET_CONTENT
                        getContent.launch(photoPickerIntent)
                    }
                    addActivityPhoto.id -> {
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
                if (data.clipData!!.itemCount > 4) {
                    shortToast("활동사진은 최대 4개까지 가능합니다.")
                    return
                } else {
                    list.clear()
                    for (i in 0 until data.clipData!!.itemCount) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        try {
                            list.add(ActivityPhotoType(activityPhoto = imageUri.toString()))
                        } catch (e: Exception) {
                            Log.e("TAG", e.toString())
                        }
                        adapter = ActivityPhotosAdapter(list)
                        binding.clubActivePicture.adapter = adapter
                    }
                    Log.d("TAG", list.toString())
                    adapter.setItemOnClickListener(object :
                        ActivityPhotosAdapter.OnItemClickListener {
                        @SuppressLint("NotifyDataSetChanged")
                        override fun onClick(position: Int) {
                            list.removeAt(position)
                            adapter.notifyDataSetChanged()
                        }
                    })
                }
            }
        }
    }
}
