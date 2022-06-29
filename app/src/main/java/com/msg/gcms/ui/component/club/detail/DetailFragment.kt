package com.msg.gcms.ui.component.club.detail

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.msg.gcms.R
import com.msg.gcms.data.local.entity.ActivityPhotoType
import com.msg.gcms.data.remote.dto.datasource.club.response.MemberSummaryResponse
import com.msg.gcms.data.remote.dto.datasource.club.response.UserInfo
import com.msg.gcms.databinding.FragmentDetailBinding
import com.msg.gcms.ui.adapter.DetailMemberAdapter
import com.msg.gcms.ui.adapter.DetailPhotoAdapter
import com.msg.gcms.ui.base.BaseDialog
import com.msg.gcms.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import com.msg.gcms.ui.component.club.ClubFragment
import com.msg.gcms.ui.component.main.MainActivity
import com.msg.gcms.utils.ItemDecorator
import com.msg.viewmodel.ClubDetailViewModel
import com.msg.viewmodel.ClubViewModel

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail),
    MainActivity.OnBackPressedListener {

    private val TAG = "DetailFragment"
    private val detailViewModel by activityViewModels<ClubDetailViewModel>()
    private val clubViewModel by activityViewModels<ClubViewModel>()
    var membersList = mutableListOf<MemberSummaryResponse>()
    var activityUrlsList = mutableListOf<ActivityPhotoType>()
    private val detailMemberAdapter = DetailMemberAdapter()
    private val detailPhotoAdaper = DetailPhotoAdapter()

    override fun init() {
        observeEvent()
        clickEvent()
        viewSet()
    }

    private fun observeEvent() {
        observeResult()
        observeStatus()
    }

    private fun clickEvent() {
        clickBackBtn()
        clickSubmitBtn()
    }

    private fun viewSet() {
        detailViewModel.setNav(false)
        settingRecyclerView()
    }

    private fun showInfo() {
        with(binding) {
            detailViewModel.result.value!!.let { it ->
                it.club.let {
                    clubName.text = it.title
                    clubBanner.load(it.bannerUrl)
                    explainClubTxt.text = it.description
                    link.text = it.notionLink
                    setTeacherInfo(it.teacher)
                    directoryTxt.text = it.contact
                }
                it.head.let {
                    bossImg.load(it.userImg) {
                        transformations(CircleCropTransformation())
                    }
                    bossName.text = it.name
                }
                clubMemberRecycler(it.member)
                clubPromotionImgRecycler(it.activityUrls)
            }
        }
    }

    private fun setTeacherInfo(name: String) {
        with(binding) {
            teacherImg.visibility = if (name == "") View.INVISIBLE else View.VISIBLE
            teacherName.visibility = if (name == "") View.INVISIBLE else View.VISIBLE
            if (name != "") teacherName.text = name
        }
    }

    private fun clickBackBtn() {
        binding.backBtn.setOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_club, ClubFragment()).commit()
        detailViewModel.setNav(true)
    }

    private fun clubMemberRecycler(member: List<UserInfo>) {
        membersList.clear()
        for (i in member.indices) {
            try {
                membersList.add(
                    MemberSummaryResponse(
                        name = member[i].name,
                        userImg = member[i].userImg.toString()
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
        binding.memberClubImg.adapter = detailMemberAdapter
        detailMemberAdapter.submitList(membersList)
    }

    private fun clubPromotionImgRecycler(photo: List<String>) {
        activityUrlsList.clear()
        for (i in photo.indices) {
            try {
                activityUrlsList.add(
                    ActivityPhotoType(
                        activityPhoto = photo[i]
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
        binding.promotionClubImg.adapter = detailPhotoAdaper
        detailPhotoAdaper.submitList(activityUrlsList)
    }

    private fun settingRecyclerView() {
        with(binding.memberClubImg) {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(50, "HORIZONTAL"))
        }

        with(binding.promotionClubImg) {
            layoutManager = GridLayoutManager(
                context,
                2,
            )
            setHasFixedSize(true)
            addItemDecoration(ItemDecorator(20, "VERTICAL"))
        }
    }

    private fun clickSubmitBtn() {
        detailViewModel.result.value!!.club.let { result ->
            binding.submitBtn.setOnClickListener {
                Log.d(TAG, "body: ${result.type}, ${result.title}")
                changeDialog()
            }
        }
    }

    private fun checkRole() {
        binding.submitBtn.let {
            when (detailViewModel.result.value!!.scope) {
                "HEAD" -> {
                    it.setBackgroundColor(resources.getColor(R.color.dark_blue))
                    it.text = getString(
                        if (detailViewModel.result.value!!.club.isOpened) {
                            R.string.close_application
                        } else R.string.open_application
                    )
                    it.visibility = View.VISIBLE
                }
                "MEMBER" -> {
                    it.visibility = View.INVISIBLE
                }
                "OTHER" -> {
                    if (detailViewModel.result.value!!.club.isOpened) {
                        if (detailViewModel.result.value!!.isApplied) {
                            it.text = getString(R.string.club_application_cancle)
                            it.setBackgroundColor(resources.getColor(R.color.pink))
                            it.visibility = View.VISIBLE
                        } else it.visibility = View.INVISIBLE
                    } else {
                        it.visibility = View.INVISIBLE
                    }
                }
                "USER" -> {
                    if (detailViewModel.result.value!!.club.isOpened) {
                        it.text = getString(R.string.club_application)
                        it.setBackgroundColor(resources.getColor(R.color.dark_blue))
                        it.visibility = View.VISIBLE
                    } else {
                        it.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun changeDialog() {
        when (detailViewModel.result.value!!.scope) {
            "HEAD" -> {
                detailViewModel.result.value!!.club.isOpened.let { open ->
                    BaseDialog(
                        if (open) {
                            getString(R.string.deadline)
                        } else {
                            getString(R.string.open)
                        },
                        if (open) {
                            getString(R.string.ask_dead_club_application)
                        } else {
                            getString(R.string.ask_open_club_application)
                        },
                        requireContext()
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            detailViewModel.result.value!!.club.let { result ->
                                if (open) {
                                    clubViewModel.putClubClose(result.type, result.title)
                                } else {
                                    clubViewModel.putClubOpen(result.type, result.title)
                                }
                            }
                            clubViewModel.getClubStatus.observe(this) {
                                dialog.dismiss()
                            }
                        }
                    }
                }
            }
            "MEMBER" -> {
            }
            "OTHER" -> {
                detailViewModel.result.value!!.club.let { result ->
                    BaseDialog(
                        getString(R.string.club_application_cancle),
                        getString(R.string.ask_cancel_application),
                        requireContext()
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            clubViewModel.postClubCancel(result.type, result.title)
                            clubViewModel.getClubStatus.observe(this) {
                                dialog.dismiss()
                            }
                        }
                    }
                }
            }
            "USER" -> {
                detailViewModel.result.value!!.club.let { result ->
                    BaseDialog(
                        getString(R.string.application),
                        getString(
                            R.string.ask_application_club, result.title
                        ),
                        requireContext()
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            clubViewModel.postClubApply(
                                result.type,
                                result.title
                            )
                            clubViewModel.getClubStatus.observe(this) {
                                dialog.dismiss()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun observeStatus() {
        clubViewModel.getClubStatus.observe(this) { status ->
            detailViewModel.result.value!!.club.let {
                detailViewModel.getDetail(it.type, it.title)
            }
            when (status) {
                401 -> shortToast("토큰이 만료되었습니다, 다시 로그인 해주세요")
                403 -> shortToast("권한이 없습니다.")
                404 -> shortToast("존재하지 않는 동아리 입니다.")
                409 -> shortToast("이미 다른 동아리에 신청 혹은 소속되어있습니다.")
                else -> shortToast("알수 없는 오류.")
            }
        }
    }

    private fun observeResult() {
        detailViewModel.result.observe(this) {
            showInfo()
            checkRole()
        }
    }

    override fun onBackPressed() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_club, ClubFragment()).commit()
        detailViewModel.setNav(true)
    }
}