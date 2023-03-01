package com.msg.gcms.presentation.view.clubmaker.club_detail

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
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
import com.msg.gcms.presentation.utils.enterActivity
import com.msg.gcms.presentation.utils.toFile
import com.msg.gcms.presentation.utils.toMultiPartBody
import com.msg.gcms.presentation.utils.uriToBitMap
import com.msg.gcms.presentation.view.intro.IntroActivity
import com.msg.gcms.presentation.viewmodel.ClubViewModel
import com.msg.gcms.presentation.viewmodel.MakeClubViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAdapter = ActivityPhotosAdapter()
        clubMemberAdapter = ClubMemberAdapter()
    }

    override fun init() {
        settingRecyclerView()
        observeEvent()
        binding.fragment = this
        Log.d("TAG", "lifeCycle: onCreateView")
    }

    override fun onResume() {
        imageSetting()
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
        if (bannerImage.isNotEmpty()) {
            binding.addBannerPicture.load(bannerImageUri) {
                crossfade(true)
                transformations(RoundedCornersTransformation(8f))
            }
            with(binding) {
                imageView7.visibility = View.GONE
                addImageTxt.visibility = View.GONE
            }
        }
        if (makeClubViewModel.activityPhotoList.isNotEmpty()) {
            activityAdapter.submitList(makeClubViewModel.activityPhotoList)
            binding.clubActivePicture.adapter = activityAdapter
        }
    }

    private fun settingRecyclerView() {
        with(binding.clubActivePicture) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(10, "HORIZONTAL"))
            binding.clubActivePicture.adapter = activityAdapter
        }
        with(binding.clubMemberRv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(50, "HORIZONTAL"))
            clubMemberRecyclerView()
        }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            if (imageUri != null) {
                val file = imageUri.toFile(requireContext())
                bannerImage.add(file.toMultiPartBody())
                bannerImageUri = imageUri

                with(binding.addBannerPicture) {
                    setImageURI(imageUri)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    binding.imageView7.visibility = View.GONE
                    binding.addImageTxt.visibility = View.GONE
                }
                binding.addBannerPicture.load(imageUri) {
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
        if (makeClubViewModel.addedMemberList.isEmpty()) {

            makeClubViewModel.addedMemberList.add(
                AddMemberType(
                    uuid = null,
                    userName = "추가하기",
                    userImg = R.drawable.bg_banner_placeholder.toString()
                )
            )
        }
        clubMemberAdapter.submitList(makeClubViewModel.addedMemberList)
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
        getContent.launch("image/*")
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
                        val imageBitmap = imageUri.uriToBitMap(requireContext())
                        makeClubViewModel.activityPhotoList.add(ActivityPhotoType(activityPhoto = imageBitmap))
                        Log.d("TAG", "getBitmap: $imageBitmap")
                        val file = imageUri.toFile(requireContext())
                        activityPhotoMultipart.add(file.toMultiPartBody())
                    }
                    Log.d("TAG", "finallyImage: ${makeClubViewModel.activityPhotoList.size}")
                    binding.clubActivePicture.adapter = activityAdapter


                    activityAdapter.setItemOnClickListener(object :
                        ActivityPhotosAdapter.OnItemClickListener {
                        override fun onClick(position: Int) {
                            makeClubViewModel.activityPhotoList.removeAt(position)
                            activityAdapter.submitList(makeClubViewModel.activityPhotoList)
                            binding.clubActivePicture.adapter = activityAdapter
                        }
                    })
                }
            }
        }
    }

    private fun observeCreateClubStatus() {
        makeClubViewModel.createClubResult.observe(this) {
            when (it) {
                // Event.Success -> {
                //     // BaseModal("생성 성공", "동아리 생성에 성공했습니다.", requireContext()).let { dialog ->
                //     //     dialog.show()
                //     //     dialog.dialogBinding.ok.setOnClickListener {
                //     //         dialog.dismiss()
                //     //         requireActivity().finish()
                //     //     }
                // }

                // }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다, 로그아웃 이후 다시 로그인해주세요.",
                        requireContext()
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(requireActivity(), IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.Conflict -> {
                    BaseModal("생성 실패", "이미 존재하는 동아리 입니다.", requireContext()).show()
                }
                Event.Success, Event.ForBidden, Event.Server -> {
                    // BaseModal("생성 실패", "이미 다른 동아리에 소속 또는 신청중입니다.", requireContext()).let { dialog ->
                    //     dialog.show()
                    //     dialog.dialogBinding.ok.setOnClickListener {
                    //         dialog.dismiss()
                    //         requireActivity().finish()
                    //     }
                    // }
                    this.findNavController()
                        .navigate(R.id.action_makeClubDetailFragment_to_makeClubResultFragment)
                }
                // Event.Server -> {
                //     BaseModal("생성 실패", "서버상의 문제가 발생하였습니다.", requireContext()).show()
                // }
                else -> {
                    this.findNavController()
                        .navigate(R.id.action_makeClubDetailFragment_to_makeClubResultFragment)
                }
            }
            clubViewModel.stopLottie()
        }
    }
}

