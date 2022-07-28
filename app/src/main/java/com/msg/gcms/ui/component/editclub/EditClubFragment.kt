package com.msg.gcms.ui.component.editclub

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.R
import com.msg.gcms.data.local.entity.ActivityPhotoType
import com.msg.gcms.databinding.FragmentEditClubBinding
import com.msg.gcms.ui.adapter.ActivityPhotosAdapter
import com.msg.gcms.ui.adapter.ClubMemberAdapter
import com.msg.gcms.ui.base.BaseFragment
import com.msg.gcms.utils.ItemDecorator
import com.msg.viewmodel.EditViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@AndroidEntryPoint
class EditClubFragment: BaseFragment<FragmentEditClubBinding>(R.layout.fragment_edit_club) {

    private val editViewModel by activityViewModels<EditViewModel>()

    private var bannerImage : MultipartBody.Part? = null
    private var bannerImageUri: Uri? = null

    private val activityPhotoMultipart = mutableListOf<MultipartBody.Part>()

    private lateinit var activityAdapter: ActivityPhotosAdapter
    private lateinit var clubMemberAdapter: ClubMemberAdapter

    override fun init() {
        binding.fragment = this
        observeEvent()
        recyclerViewSetting()
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK) {
                val imageUrl = it.data?.data
                val file = File(getPathFromUri(imageUrl))
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                val img = MultipartBody.Part.createFormData("files", file.name, requestFile)
                Log.d("TAG", "onActivityResult: $img")
                bannerImage = img
                bannerImageUri = imageUrl!!
                with(binding) {
                    bannerImageView.load(imageUrl) {
                        crossfade(true)
                        transformations(RoundedCornersTransformation(8f, 8f, 8f, 8f))
                    }
                }
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && resultCode == requestCode) {
            if (data?.clipData != null) {
                if (data.clipData!!.itemCount + editViewModel.beforeActivityPhotoList.size > 4) {
                    shortToast("활동사진은 최대 4개까지 가능합니다.")
                    return
                } else {
                    editViewModel.beforeActivityPhotoList.clear()
                    activityPhotoMultipart.clear()
                    for (i in 0 until data.clipData!!.itemCount) {
                        val imageUri: Uri = data.clipData!!.getItemAt(i).uri
                        editViewModel.afterActivityPhotoList.add(ActivityPhotoType(activityPhoto = imageUri))
                        // activityAdapter = ActivityPhotosAdapter(editViewModel.beforeActivityPhotoList)
                        val file = File(getPathFromUri(imageUri))
                        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                        val img = MultipartBody.Part.createFormData("files", file.name, requestFile)
                        Log.d("TAG", "onActivityResult: $img")
                        activityPhotoMultipart.add(img)
                    }
                    binding.clubActivePictureRv.adapter = activityAdapter

                    activityAdapter.setItemOnClickListener(object :
                        ActivityPhotosAdapter.OnItemClickListener {
                        override fun onClick(position: Int) {
                            editViewModel.beforeActivityPhotoList.removeAt(position)
                            activityAdapter.notifyDataSetChanged()
                        }
                    })
                }
            }
        }
    }

    fun buttonClickListener(view: View) {
        when(view.id) {
            binding.backBtn.id -> clickBackBtn()
            binding.completeBtn.id -> editClub()
        }
    }

    fun galleryOpen(view: View) {
        if(ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED) {
            when(view.id) {
                binding.bannerImageView.id -> addBanner()
                binding.addActivityPhotoBtn.id -> addActivityPhoto()
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
            )
        }
    }

    private fun addBanner() {
        val photoPickIntent = Intent(Intent.ACTION_PICK)
        photoPickIntent.type = "image/*"
        photoPickIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        getContent.launch(photoPickIntent)
    }

    private fun addActivityPhoto() {
        val activityPhotosIntent = Intent(Intent.ACTION_PICK)
        activityPhotosIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        activityPhotosIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
    }

    private fun recyclerViewSetting() {
        with(binding.clubActivePictureRv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(10, "HORIZONTAL"))
        }
        with(binding.clubMemberRv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(50, "HORIZONTAL"))
            clubMemberRecyclerView()
        }
    }

    private fun observeEvent() {
        observeClubInfo()
        observeClubTypeDivider()
    }

    private fun observeClubTypeDivider(){
        editViewModel.clubName.observe(this) {
            getClubInfo()
        }
    }

    private fun observeClubInfo() {
        editViewModel.clubInfo.observe(this) {
            if(it != null) {
                with(binding){
                    clubNameEt.setText(it.club.title)
                    clubDescriptionEt.setText(it.club.description)
                    LinkEt.setText(it.club.notionLink)
                    contactEt.setText(it.club.contact)
                    teacherNameEt.setText(it.club.teacher)
                    bannerImageView.load(it.club.bannerUrl) {
                        crossfade(true)
                        transformations(RoundedCornersTransformation(8f, 8f, 8f, 8f))
                    }
                    bannerIcon.visibility = View.GONE
                    bannerTxt.visibility = View.GONE
                    clubMemberAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getClubInfo() {
        Log.d("TAG", "getClubInfo")
        editViewModel.getClubInfo()
    }

    private fun clickBackBtn() {
        requireActivity().finish()
    }

    private fun clubMemberRecyclerView() {
        clubMemberAdapter = ClubMemberAdapter(editViewModel.memberList)
        clubMemberAdapter.setItemOnClickListener(object : ClubMemberAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                findNavController().navigate(R.id.action_makeClubDetailFragment_to_studentSearchFragment)
            }
        })
        binding.clubMemberRv.adapter = clubMemberAdapter
    }

    private fun editClub() {

    }

    @SuppressLint("Range")
    private fun getPathFromUri(uri: Uri?): String {
        val cursor: Cursor? = requireActivity().contentResolver.query(uri!!, null, null, null, null)
        cursor?.moveToNext()
        val path: String = cursor!!.getString(cursor.getColumnIndex("_data"))
        cursor.close()
        return path
    }
}