package com.msg.gcms.ui.component.clubmaker.clubDetail

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentMakeClubDetailBinding
import com.msg.gcms.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeClubDetailFragment :
    BaseFragment<FragmentMakeClubDetailBinding>(R.layout.fragment_make_club_detail) {

    override fun init() {
        binding.fragment = this
        settingRecyclerView()
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
            binding.nextBtn.id -> {
                activity?.finish()
            }
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
                    clubActivePicture.id -> {
                        if (Build.VERSION.SDK_INT < 19) {
                            val intent = Intent()
                            intent.type = "image/*"
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                            intent.action = Intent.ACTION_GET_CONTENT
                            startActivityForResult(
                                Intent.createChooser(intent, "사진을 선택해주세요"),
                                200
                            )
                        } else {
                            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                            intent.addCategory(Intent.CATEGORY_OPENABLE)
                            intent.type = "image/*"
                            startActivityForResult(intent, 200)
                        }
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
                for (i in 0..3) {
                    val imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                }
            }
        }
    }

    private fun settingRecyclerView() {
        with(binding.clubActivePicture) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}
