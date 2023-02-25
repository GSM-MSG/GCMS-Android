package com.msg.gcms.presentation.view.editclub

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentEditClubBinding
import com.msg.gcms.domain.data.club.get_club_detail.ClubMemberData
import com.msg.gcms.domain.data.club.modify_club_info.ModifyClubInfoData
import com.msg.gcms.presentation.adapter.activity_photo.ActivityPhotoType
import com.msg.gcms.presentation.adapter.activity_photo.ActivityPhotosAdapter
import com.msg.gcms.presentation.adapter.add_member.AddMemberType
import com.msg.gcms.presentation.adapter.club_member.ClubMemberAdapter
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.base.BaseModal
import com.msg.gcms.presentation.utils.ItemDecorator
import com.msg.gcms.presentation.utils.toFile
import com.msg.gcms.presentation.utils.toMultiPartBody
import com.msg.gcms.presentation.utils.uriToBitMap
import com.msg.gcms.presentation.viewmodel.EditViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.UUID

@AndroidEntryPoint
class EditClubFragment : BaseFragment<FragmentEditClubBinding>(R.layout.fragment_edit_club) {

    private val editViewModel by activityViewModels<EditViewModel>()

    private var bannerImage: MultipartBody.Part? = null

    // private var bannerImageUri: Uri? = null
    // private var bannerImageBitmap: Bitmap? = null

    private val activityPhotoMultipart = mutableListOf<MultipartBody.Part>()

    private lateinit var activityAdapter: ActivityPhotosAdapter
    private lateinit var clubMemberAdapter: ClubMemberAdapter

    private val activityPhotoList = mutableListOf<ActivityPhotoType>()
    private var activityPhotoUrlList = mutableListOf<String>()

    var getClubInfoListener = false

    private var isBannerImageResponse = true
    private var isActivityImageResponse = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clubMemberAdapter = ClubMemberAdapter()
        activityAdapter = ActivityPhotosAdapter()
    }

    override fun init() {
        binding.fragment = this
        observeEvent()
        recyclerViewSetting()
        memberRecyclerviewUpdater(editViewModel.addedMemberData.value ?: listOf())
    }

    private fun observeEvent() {
        observeClubInfo()
        observeConvertImage()
        observeEditClubResult()
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            if (imageUri != null) {
                val file = imageUri.toFile(requireContext())
                bannerImage = file.toMultiPartBody()
                isBannerImageResponse = false
                with(binding) {
                    bannerImageView.load(imageUri) {
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
                if (data.clipData!!.itemCount + activityPhotoList.size > 4) {
                    shortToast("활동사진은 최대 4개까지 가능합니다.")
                    return
                } else {
                    for (i in 0 until data.clipData!!.itemCount) {
                        val imageUri: Uri = data.clipData!!.getItemAt(i).uri
                        val imageBitmap = imageUri.uriToBitMap(requireContext())
                        activityPhotoList.add(ActivityPhotoType(activityPhoto = imageBitmap))
                        activityPhotoRecyclerViewUpdater(activityPhotoList)
                        val file = imageUri.toFile(requireContext())
                        activityPhotoMultipart.add(file.toMultiPartBody())
                        isActivityImageResponse = false
                    }
                    Log.d("TAG", "activityPhotoList: $activityPhotoList")
                    activityPhotoRecyclerViewUpdater(activityPhotoList)
                }
            }
        }
    }

    fun buttonClickListener(view: View) {
        when (view.id) {
            binding.backBtn.id -> clickBackBtn()
            binding.completeBtn.id -> editClub()
        }
    }

    fun galleryOpen(view: View) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            when (view.id) {
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
        getContent.launch("image/*")
    }

    private fun addActivityPhoto() {
        val activityPhotosIntent = Intent(Intent.ACTION_PICK)
        activityPhotosIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        activityPhotosIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(activityPhotosIntent, Activity.RESULT_OK)
    }

    private fun recyclerViewSetting() {
        with(binding.clubActivePictureRv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(10, "HORIZONTAL"))
            activityPhotoRecyclerView()

        }
        with(binding.clubMemberRv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(50, "HORIZONTAL"))
            clubMemberRecyclerView()
        }
    }

    private fun observeConvertImage() {
        editViewModel.convertBannerImage.observe(this) {
            if (it.isNotEmpty()) {
                isBannerImageResponse = true
                if (isActivityImageResponse)
                    editClubInfo()
            }
        }
        editViewModel.convertActivityPhoto.observe(this) {
            if (it.isNotEmpty()) {
                isActivityImageResponse = true
                if (isBannerImageResponse)
                    editClubInfo()
            }
        }
    }

    private fun clubMemberChecker(list: List<ClubMemberData>): List<AddMemberType> {
        return if (list.isEmpty()) {
            listOf(
                AddMemberType(
                    uuid = null,
                    userName = "추가하기",
                    userImg = null
                )
            )
        } else {
            list.map {
                AddMemberType(
                    uuid = it.uuid,
                    userName = it.name,
                    userImg = it.userImg
                )
            }
        }
    }

    private fun memberRecyclerviewUpdater(list: List<AddMemberType>) {
        clubMemberAdapter.submitList(list)
        binding.clubMemberRv.adapter = clubMemberAdapter
    }

    private fun activityPhotoRecyclerViewUpdater(list: List<ActivityPhotoType>) {
        activityAdapter.submitList(list)
        binding.clubActivePictureRv.adapter = activityAdapter
    }

    private fun observeClubInfo() {
        editViewModel.clubInfo.observe(this) {
            if (!getClubInfoListener) {
                getClubInfoListener = true
                if (it != null) {
                    with(binding) {
                        clubNameEt.setText(it.name)
                        clubDescriptionEt.setText(it.content)
                        linkEt.setText(it.notionLink)
                        contactEt.setText(it.contact)
                        teacherNameEt.setText(it.teacher)
                        bannerImageView.load(it.bannerImg) {
                            crossfade(true)
                            transformations(RoundedCornersTransformation(8f, 8f, 8f, 8f))
                        }
                        bannerIcon.visibility = View.GONE
                        bannerTxt.visibility = View.GONE
                        activityPhotoUrlList = it.activityImgs.toMutableList()
                        memberRecyclerviewUpdater(clubMemberChecker(it.member))
                        lifecycleScope.launch {
                            val activityPhotoList = addBitmapToList(it.activityImgs)
                            this@EditClubFragment.activityPhotoList.addAll(activityPhotoList)
                            activityPhotoRecyclerViewUpdater(activityPhotoList)
                        }
                    }
                }
            } else {
                with(binding) {
                    bannerImageView.load(it.bannerImg) {
                        crossfade(true)
                        transformations(RoundedCornersTransformation(8f, 8f, 8f, 8f))
                    }
                    bannerIcon.visibility = View.GONE
                    bannerTxt.visibility = View.GONE

                }
            }
        }
    }

    private suspend fun addBitmapToList(list: List<String>): List<ActivityPhotoType> {
        val activityPhotoList = mutableListOf<ActivityPhotoType>()
        Log.d("TAG", "beforeBitmap: ${editViewModel.clubInfo.value?.activityImgs}")
        list.forEach { activityPhotoList.add(ActivityPhotoType(getBitmapFromUrl(it))) }
        return activityPhotoList
    }

    private fun clickBackBtn() {
        requireActivity().finish()
    }

    private fun activityPhotoRecyclerView() {
        activityAdapter.setItemOnClickListener(object :
            ActivityPhotosAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                activityPhotoList.removeAt(position)
                activityPhotoRecyclerViewUpdater(activityPhotoList)
            }
        })
        binding.clubActivePictureRv.adapter = activityAdapter
    }

    private fun clubMemberRecyclerView() {
        clubMemberAdapter.setItemOnClickListener(object : ClubMemberAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                findNavController().navigate(R.id.action_editClubFragment_to_editSearchFragment)
            }
        })
        binding.clubMemberRv.adapter = clubMemberAdapter
    }

    private suspend fun getBitmapFromUrl(url: String): Bitmap {
        val loading = ImageLoader(requireContext())
        val request: ImageRequest = ImageRequest.Builder(requireContext())
            .data(url)
            .build()
        val result: Drawable = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun editClub() {
        startLottie()
        fieldCheck()
    }

    private fun startLottie() {
        editViewModel.startLottie(fragmentManager = requireActivity().supportFragmentManager)
    }

    private fun fieldCheck() {
        if (binding.clubNameEt.text.isNotEmpty()
            && binding.clubDescriptionEt.text.isNotEmpty()
            && binding.linkEt.text.isNotEmpty()
            && binding.contactEt.text.isNotEmpty()
        ) {
            if (binding.linkEt.text.startsWith("http://") || binding.linkEt.text.toString()
                    .startsWith("https://")
            ) {
                imageUpload(bannerImage = bannerImage!!, activityPhotoList = activityPhotoList)
            } else {
                editViewModel.stopLottie()
                shortToast("링크 형식으로 입력해주세요")
            }
        } else {
            editViewModel.stopLottie()
            shortToast("필수 기입 요소들을 다시 확인해주세요!!")
        }
    }

    private fun imageUpload(
        bannerImage: MultipartBody.Part,
    ) {
        imageBannerUploadToServer(list = listOf(bannerImage))
    }

    private fun imageUpload(
        activityPhotoList: List<ActivityPhotoType>
    ) {
        val activityPhoto = activityPhotoList.map { convertBitmapToMultiPart(it.activityPhoto) }

        activityPhotoUploadToServer(activityPhoto)
    }

    private fun imageUpload(
        bannerImage: MultipartBody.Part,
        activityPhotoList: List<ActivityPhotoType>
    ) {
        Log.d("TAG", "imageUploadLogic")

        val activityPhoto = activityPhotoList.map { convertBitmapToMultiPart(it.activityPhoto) }

        activityPhotoUploadToServer(activityPhoto)

        imageBannerUploadToServer(list = listOf(bannerImage))

        // newPhotosList = convertBitmapToFile(newActivityPhoto).toMutableList()
        // newPhotosList.add(
        //     if (bannerImage != null) bannerImage!! else convertBitmapToMultiPart(
        //         bannerImageBitmap!!
        //     )
        // )
        // imageUploadToServer(newPhotosList)
    }

    // private fun convertBitmapToFile(list: List<ActivityPhotoType>): List<MultipartBody.Part> {
    //
    //     val imgList = mutableListOf<MultipartBody.Part>()
    //
    //     list.forEach {
    //         val wrapper = ContextWrapper(context)
    //         var file = wrapper.getDir("images", Context.MODE_PRIVATE)
    //         file = File(file, "${UUID.randomUUID()}.jpg}")
    //
    //         try {
    //             val stream: OutputStream = FileOutputStream(file)
    //             it.activityPhoto.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    //             stream.flush()
    //             stream.close()
    //
    //             imgList.add(file.toMultiPartBody())
    //             Log.d("TAG", "convertBitmapToMultiPart: $imgList")
    //         } catch (e: Exception) {
    //             e.printStackTrace()
    //         }
    //     }
    //     return imgList
    // }

    private fun imageBannerUploadToServer(list: List<MultipartBody.Part>) {
        editViewModel.uploadBannerImage(list = list)
    }

    private fun activityPhotoUploadToServer(list: List<MultipartBody.Part>) {
        editViewModel.uploadActivityPhoto(list = list)
    }

    private fun convertBitmapToMultiPart(bitmap: Bitmap): MultipartBody.Part {
        val wrapper = ContextWrapper(context)
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg}")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.toMultiPartBody()
    }

    private fun editClubInfo() {
        editViewModel.putChangeClubInfo(
            ModifyClubInfoData(
                type = editViewModel.clubInfo.value!!.type,
                title = binding.clubNameEt.text.toString().trim(),
                description = binding.clubDescriptionEt.text.toString(),
                contact = binding.contactEt.text.toString(),
                notionLink = binding.linkEt.text.toString(),
                teacher = binding.teacherNameEt.text.toString(),
                bannerUrl = editViewModel.convertBannerImage.value!!.first(),
                activityImgs = editViewModel.convertActivityPhoto.value!!,
                member = editViewModel.addedMemberData.value!!.filter { it.uuid != null }
                    .map { it.uuid!! },
            ),
            clubId = editViewModel.clubInfo.value!!.id
        )
    }

    private fun observeEditClubResult() {
        editViewModel.editClubResult.observe(this) {
            when (it) {
                Event.Success -> {
                    shortToast("동아리 정보가 수정되었습니다.")
                    requireActivity().finish()
                }
                // Event.BadRequest -> {
                // }
                Event.Unauthorized -> {
                    editViewModel.stopLottie()
                    BaseModal(context = requireContext(), title = "시간 만료", msg = "앱을 재실행 해주세요!!")
                }
                // Event.ForBidden -> {
                // }
                // Event.NotFound -> {
                //
                // }
                // Event.Conflict -> {
                // }
                Event.Server -> {
                    editViewModel.stopLottie()
                    BaseModal(
                        context = requireContext(),
                        title = "서버 오류",
                        msg = "서버에 일시적인 오류로 인해 해당 기능의 사용이 제한됩니다."
                    )
                }
                Event.UnKnown -> {
                    editViewModel.stopLottie()
                    BaseModal(
                        context = requireContext(),
                        title = "동아리 정보 수정",
                        msg = "동아리 정보 수정에 실패하였습니다."
                    )
                }
                else -> {
                    BaseModal(
                        context = requireContext(),
                        title = "오류 발생",
                        msg = "알 수 없는 오류가 발생하였습니다. 개발자에게 문의해주세요"
                    )
                }
            }
        }
    }
}