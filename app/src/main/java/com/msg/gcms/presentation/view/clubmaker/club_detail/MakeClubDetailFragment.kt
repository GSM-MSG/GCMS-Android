package com.msg.gcms.presentation.view.clubmaker.club_detail

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentMakeClubDetailBinding
import com.msg.gcms.presentation.adapter.activity_photo.ActivityPhotoType
import com.msg.gcms.presentation.adapter.activity_photo.ActivityPhotosAdapter
import com.msg.gcms.presentation.adapter.add_member.AddMemberType
import com.msg.gcms.presentation.adapter.club_member.ClubMemberAdapter
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.base.BaseModal
import com.msg.gcms.presentation.utils.ItemDecorator
import com.msg.gcms.presentation.viewmodel.ClubViewModel
import com.msg.gcms.presentation.viewmodel.MakeClubViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@AndroidEntryPoint
class MakeClubDetailFragment :
    BaseFragment<FragmentMakeClubDetailBinding>(R.layout.fragment_make_club_detail) {

    private val makeClubViewModel by activityViewModels<MakeClubViewModel>()
    private val clubViewModel by activityViewModels<ClubViewModel>()

    private val activityPhotoMultipart = mutableListOf<MultipartBody.Part>()
    private val bannerImage = mutableListOf<MultipartBody.Part>()

    private lateinit var activityAdapter: ActivityPhotosAdapter
    private lateinit var clubMemberAdapter: ClubMemberAdapter

    private var bannerImageUri: Uri? = null

    private var imageState = false

    override fun init() {
        binding.fragment = this
        settingRecyclerView()
        observeEvent()
    }

    override fun onResume() {
        imageSetting()
        settingRecyclerView()
        super.onResume()
    }

    private fun observeEvent() {
        observeImageChange()
        observeCreateClubStatus()
    }

    private fun observeImageChange() {
        makeClubViewModel.imageUploadCheck.observe(requireActivity()) {
            if (it != null) {
                if (!imageState) {
                    postCreateClub()
                    Log.d("TAG", "observeImageChange: 카운트")
                    imageState = true
                }
            }
        }
    }

    private fun postCreateClub() {
        makeClubViewModel.createClub()
    }

    private fun imageSetting() {
        if (bannerImage.isNotEmpty() || makeClubViewModel.activityPhotoList.isNotEmpty() || makeClubViewModel.memberList.isNotEmpty()) {
            binding.addBannerPicture.load(bannerImageUri) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8f))
            }
            with(binding) {
                imageView7.visibility = View.GONE
                addImageTxt.visibility = View.GONE
            }
        }
    }

    private fun settingRecyclerView() {
        with(binding.clubActivePicture) {
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

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val imageUrl = it.data?.data
                val file = File(getPathFromUri(imageUrl))
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                val img = MultipartBody.Part.createFormData("file", file.name, requestFile)
                Log.d("TAG", "onActivityResult: $img")
                bannerImage.add(img)
                bannerImageUri = imageUrl!!

                with(binding.addBannerPicture) {
                    setImageURI(imageUrl)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    binding.imageView7.visibility = View.GONE
                    binding.addImageTxt.visibility = View.GONE
                }
                binding.addBannerPicture.load(imageUrl) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(8f))
                }
            }
        }

    fun clickedPageBtn(view: View) {
        when (view.id) {
            binding.clubTypeBackBtn.id -> {
                this.findNavController().popBackStack()
            }
            binding.nextBtn.id -> {
                photoCheck()
            }
        }
    }

    private fun photoCheck() {
        if (bannerImageUri == null) {
            shortToast("배너 이미지를 삽입하여 주세요!!")
        } else {
            if (activityPhotoMultipart.isNotEmpty()) {
                makeClubViewModel.activityPhotoUpload(activityPhotoMultipart)
            } else {
                makeClubViewModel.setActivityPhotoUpload()
                makeClubViewModel.imageUploadCheck()
            }
            makeClubViewModel.bannerImageUpload(bannerImage)
            progressSetting()
        }
    }

    private fun progressSetting() {
        clubViewModel.startLottie(requireActivity().supportFragmentManager)
    }

    private fun clubMemberRecyclerView() {
        if (makeClubViewModel.memberList.isEmpty()) {

            //TODO 여기 로직 다 만들어야함
            makeClubViewModel.memberList.add(
                AddMemberType(
                    uuid = null,
                    userName = "추가하기",
                    userImg = R.drawable.bg_banner_placeholder.toString()
                )
            )
        }
        // Todo 여기 로직도 수정하기
        clubMemberAdapter = ClubMemberAdapter(makeClubViewModel.memberList)
        clubMemberAdapter.setItemOnClickListener(object : ClubMemberAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                findNavController().navigate(R.id.action_makeClubDetailFragment_to_studentSearchFragment)
            }
        })
        binding.clubMemberRv.adapter = clubMemberAdapter
    }

    fun galleryOpen(view: View) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            with(binding) {
                when (view.id) {
                    addBannerPicture.id -> choseBannerGalley()
                    addActivityPhoto.id -> choseActivityPhotos()
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
            )
        }
    }

    private fun choseBannerGalley() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        photoPickerIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        getContent.launch(photoPickerIntent)
    }

    private fun choseActivityPhotos() {
        val activityPhotosIntent = Intent(Intent.ACTION_PICK)
        activityPhotosIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        activityPhotosIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(activityPhotosIntent, Activity.RESULT_OK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && resultCode == requestCode) {
            if (data?.clipData != null) {
                if (data.clipData!!.itemCount + makeClubViewModel.activityPhotoList.size > 4) {
                    shortToast("활동사진은 최대 4개까지 가능합니다.")
                    return
                } else {
                    makeClubViewModel.activityPhotoList.clear()
                    activityPhotoMultipart.clear()
                    for (i in 0 until data.clipData!!.itemCount) {
                        val imageUri: Uri = data.clipData!!.getItemAt(i).uri
                        val imageBitmap = uriToBitMap(imageUri)
                        makeClubViewModel.activityPhotoList.add(ActivityPhotoType(activityPhoto = imageBitmap))
                        Log.d("TAG", "getBitmap: $imageBitmap")
                        activityAdapter = ActivityPhotosAdapter(makeClubViewModel.activityPhotoList)
                        val file = File(getPathFromUri(imageUri))
                        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                        val img = MultipartBody.Part.createFormData("file", file.name, requestFile)
                        Log.d("TAG", "onActivityResult: $img")
                        activityPhotoMultipart.add(img)
                    }
                    Log.d("TAG", "finallyImage: ${makeClubViewModel.activityPhotoList.size}")
                    binding.clubActivePicture.adapter = activityAdapter


                    activityAdapter.setItemOnClickListener(object :
                        ActivityPhotosAdapter.OnItemClickListener {
                        override fun onClick(position: Int) {
                            makeClubViewModel.activityPhotoList.removeAt(position)
                            activityAdapter.notifyDataSetChanged()
                        }
                    })
                }
            }
        }
    }

    private fun uriToBitMap(imageUri: Uri): Bitmap {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    requireContext().contentResolver,
                    imageUri
                )
            )
        } else {
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
        }
        return bitmap
    }

    @SuppressLint("Range")
    private fun getPathFromUri(uri: Uri?): String {
        val cursor: Cursor? = requireActivity().contentResolver.query(uri!!, null, null, null, null)
        cursor?.moveToNext()
        val path: String = cursor!!.getString(cursor.getColumnIndex("_data"))
        cursor.close()
        return path
    }

    private fun observeCreateClubStatus() {
        makeClubViewModel.createClubResult.observe(this) {
            when (it) {
                Event.Success -> {
                    BaseModal("생성 성공", "동아리 생성에 성공했습니다.", requireContext()).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            requireActivity().finish()
                        }
                    }
                }
                Event.BadRequest -> {
                    BaseModal("생성 실패", "이미 다른 동아리에 소속 또는 신청중입니다.", requireContext()).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            requireActivity().finish()
                        }
                    }
                }
                Event.Conflict -> {
                    BaseModal("생성 실패", "이미 존재하는 동아리 입니다.", requireContext()).show()
                }
                Event.Unauthorized -> {
                    BaseModal("오류", "토큰이 만료되었습니다, 앱 종료후 다시 실행해 주세요", requireContext()).show()
                }
                Event.Server -> {
                    Log.d("TAG", "observeStatus: $it")
                }
                else -> {
                    BaseModal("생성 실패", "알수 없는 오류 발생, 개발자에게 문의해주세요", requireContext()).show()
                }
            }
            clubViewModel.stopLottie()
        }
    }
}

