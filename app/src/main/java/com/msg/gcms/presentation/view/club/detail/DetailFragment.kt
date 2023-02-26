package com.msg.gcms.presentation.view.club.detail

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentDetailBinding
import com.msg.gcms.domain.data.club.get_club_detail.ClubMemberData
import com.msg.gcms.domain.data.club_member.get_club_member.MemberData
import com.msg.gcms.presentation.adapter.detail_member.DetailMemberAdapter
import com.msg.gcms.presentation.adapter.detail_photo.DetailPhotoAdapter
import com.msg.gcms.presentation.adapter.detail_photo.PromotionPicType
import com.msg.gcms.presentation.adapter.detail_side_bar.DetailPageSideBar
import com.msg.gcms.presentation.adapter.detail_side_bar.DetailSideBarAdapter
import com.msg.gcms.presentation.base.BaseDialog
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.base.BaseModal
import com.msg.gcms.presentation.utils.ItemDecorator
import com.msg.gcms.presentation.utils.enterActivity
import com.msg.gcms.presentation.utils.exitActivity
import com.msg.gcms.presentation.utils.exitFragment
import com.msg.gcms.presentation.view.club.ClubFragment
import com.msg.gcms.presentation.view.editclub.EditClubActivity
import com.msg.gcms.presentation.view.intro.IntroActivity
import com.msg.gcms.presentation.view.main.MainActivity
import com.msg.gcms.presentation.view.member_manage.MemberManageActivity
import com.msg.gcms.presentation.view.profile.ProfileActivity
import com.msg.gcms.presentation.viewmodel.ClubDetailViewModel
import com.msg.gcms.presentation.viewmodel.ClubViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail),
    MainActivity.OnBackPressedListener {

    private val TAG = "DetailFragment"
    private val detailViewModel by activityViewModels<ClubDetailViewModel>()
    private val clubViewModel by activityViewModels<ClubViewModel>()
    private lateinit var callback: OnBackPressedCallback
    var membersList = mutableListOf<MemberData>()
    var activityUrlsList = mutableListOf<PromotionPicType>()
    private val detailMemberAdapter = DetailMemberAdapter()
    private val detailPhotoAdapter = DetailPhotoAdapter()
    private lateinit var sideBarAdapter: DetailSideBarAdapter

    private val headSideBarItem = arrayListOf(
        DetailPageSideBar("동아리 멤버 관리하기", R.drawable.ic_person_two),
        DetailPageSideBar("동아리 정보 수정하기", R.drawable.ic_edit),
        DetailPageSideBar("동아리 삭제하기", R.drawable.ic_club_delete)
    )

    private val memberSideBarItem = arrayListOf(
        DetailPageSideBar("동아리 멤버 확인하기", R.drawable.ic_person_two),
        DetailPageSideBar("동아리 탈퇴하기", R.drawable.ic_club_delete)
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitActivity(requireActivity())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onStop() {
        super.onStop()
        detailViewModel.initializationProperties()
        clubViewModel.initializationProperties()
    }

    override fun init() {
        observeEvent()
        clickEvent()
        viewSet()
        binding.detail = this
    }

    private fun observeEvent() {
        observeResult()
        observeStatus()
        observeClubDetailInfo()
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
                clubName.text = it.name
                clubBanner.load(it.bannerImg)
                explainClubTxt.text = it.content
                link.text = it.notionLink
                setTeacherInfo(it.teacher)
                directoryTxt.text = it.contact
                it.head.let {
                    bossImg.load(it.userImg ?: R.drawable.ic_default_profile) {
                        transformations(CircleCropTransformation())
                    }
                    bossName.text = it.name
                }
                clubMemberRecycler(it.member)
                clubPromotionImgRecycler(it.activityImgs)
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
        if (detailViewModel.isProfile.value == true) {
            exitActivity(requireActivity())
        } else {
            detailViewModel.setNav(true)
            exitFragment(requireActivity(), R.id.fragment_club, ClubFragment())
        }
        detailViewModel.setIsProfile(false)
    }

    private fun clubMemberRecycler(member: List<ClubMemberData>) {
        membersList.clear()
        for (i in member.indices) {
            try {
                membersList.add(
                    MemberData(
                        uuid = member[i].uuid,
                        name = member[i].name,
                        userImg = member[i].userImg,
                        email = member[i].email,
                        `class` = member[i].`class`,
                        num = member[i].num,
                        grade = member[i].grade,
                        scope = "MEMBER"
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
                    PromotionPicType(
                        promotionUrl = photo[i]
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
        binding.promotionClubImg.adapter = detailPhotoAdapter
        detailPhotoAdapter.submitList(activityUrlsList)
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
        binding.submitBtn.setOnClickListener {
            changeDialog()
        }
    }

    fun clickSideBar(view: View) {
        binding.drawerLayout.openDrawer(binding.sideBar)
    }

    private fun sideBarRvSetting() {
        with(binding.sideBarRv) {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
        }
        sideBarAdapter.setItemOnClickListener(object : DetailSideBarAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                if (sideBarAdapter.itemCount == 2) {
                    when (position) {
                        0 -> {
                            goManageActivity()
                        }
                        1 -> {
                            BaseDialog("동아리 탈퇴", "정말 나갈꺼에요??", context!!).let { dialog ->
                                dialog.show()
                                dialog.dialogBinding.ok.setOnClickListener {
                                    clubViewModel.exit(
                                        clubId = detailViewModel.result.value!!.id
                                    )
                                    dialog.dismiss()
                                    // requireActivity().supportFragmentManager.beginTransaction()
                                    //     .replace(R.id.fragment_club, DetailFragment()).commit()
                                }
                            }
                        }
                    }
                } else {
                    when (position) {
                        0 -> {
                            goManageActivity()
                        }
                        1 -> {
                            val intent = Intent(context, EditClubActivity::class.java)
                            intent.putExtra(
                                "clubId",
                                detailViewModel.result.value!!.id
                            )
                            startActivity(intent)
                        }
                        2 -> {
                            BaseDialog("동아리 삭제", "정말 삭제할꺼에요??", context!!).let { dialog ->
                                dialog.show()
                                dialog.dialogBinding.ok.setOnClickListener {
                                    clubViewModel.deleteClub(
                                        detailViewModel.result.value!!.id
                                    )
                                    dialog.dismiss()
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_club, ClubFragment()).commit()
                                    detailViewModel.setNav(true)
                                }
                            }
                        }
                    }
                }
            }
        })
        binding.sideBarRv.adapter = sideBarAdapter
    }

    private fun goManageActivity() {
        val intent = Intent(context, MemberManageActivity::class.java)
        intent.putExtra("clubId", detailViewModel.result.value!!.id)
        intent.putExtra("role", detailViewModel.result.value!!.scope)
        startActivity(intent)
    }

    private fun checkRole() {
        binding.submitBtn.let {
            when (detailViewModel.result.value!!.scope) {
                "HEAD" -> {
                    it.setBackgroundColor(
                        ContextCompat.getColor(
                            requireActivity(),
                            R.color.dark_blue
                        )
                    )
                    it.text = getString(
                        if (detailViewModel.result.value!!.isOpened) {
                            R.string.close_application
                        } else R.string.open_application
                    )
                    it.visibility = View.VISIBLE
                    sideBarAdapter = DetailSideBarAdapter(headSideBarItem)
                    sideBarRvSetting()
                }
                "MEMBER" -> {
                    it.visibility = View.INVISIBLE
                    sideBarAdapter = DetailSideBarAdapter(memberSideBarItem)
                    sideBarRvSetting()
                }
                "OTHER" -> {
                    it.visibility = View.INVISIBLE
                    binding.sideBarBtn.visibility = View.GONE
                }
                "USER" -> {
                    if (detailViewModel.result.value!!.isOpened) {
                        detailViewModel.result.value!!.isApplied.let { applied ->
                            it.text =
                                getString(if (applied) R.string.club_application_cancle else R.string.club_application)
                            it.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireActivity(),
                                    if (applied) R.color.pink else R.color.dark_blue
                                )
                            )
                        }
                        it.visibility = View.VISIBLE
                    } else {
                        it.visibility = View.INVISIBLE
                    }
                    binding.sideBarBtn.visibility = View.GONE
                }
            }
        }
    }

    private fun changeDialog() {
        when (detailViewModel.result.value!!.scope) {
            "HEAD" -> {
                detailViewModel.result.value!!.isOpened.let { open ->
                    BaseDialog(
                        getString(if (open) R.string.deadline else R.string.open),
                        getString(if (open) R.string.ask_dead_club_application else R.string.ask_open_club_application),
                        requireContext()
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            detailViewModel.result.value!!.let { result ->
                                if (open) {
                                    clubViewModel.putClubClose(clubId = result.id)
                                } else {
                                    clubViewModel.putClubOpen(clubId = result.id)
                                }
                            }
                            dialog.dismiss()
                        }
                    }
                }
            }
            "USER" -> {
                detailViewModel.result.value!!.let { result ->
                    detailViewModel.result.value!!.isApplied.let { applied ->
                        BaseDialog(
                            getString(if (applied) R.string.cancel else R.string.application),
                            getString(
                                if (applied) R.string.ask_cancel_application else R.string.ask_application_club,
                                result.name
                            ),
                            requireContext()
                        ).let { dialog ->
                            dialog.show()
                            dialog.dialogBinding.ok.setOnClickListener {
                                if (applied) {
                                    clubViewModel.postClubCancel(
                                        clubId = detailViewModel.result.value!!.id
                                    )
                                } else
                                    clubViewModel.postClubApply(
                                        clubId = detailViewModel.result.value!!.id
                                    )
                                dialog.dismiss()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun observeStatus() {
        observeApplyClubEvent()
        observeCancelClubEvent()
        observeOpenClubApplyEvent()
        observeCloseClubApplyEvent()
        observeExitClubEvent()
    }

    private fun observeApplyClubEvent() {
        clubViewModel.applyClub.observe(this) { status ->
            detailViewModel.refreshDetailInfo(detailViewModel.result.value!!.id)
            when (status) {
                Event.Success -> {
                    BaseModal("성공", "동아리 신청에 성공했습니다.", requireContext()).show()
                }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다. 다시 로그인해주세요.",
                        requireContext()
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(requireActivity(), IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.ForBidden -> {
                    BaseModal("실패", "이미 다른 동아리에 소속 또는 신청 중입니다.", requireContext()).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", requireContext()).show()
                }
                else -> {
                    BaseModal("오류", "알 수 없는 오류 발생, 개발자에게 문의해주세요.", requireContext()).show()
                }
            }
        }
    }

    private fun observeCancelClubEvent() {
        clubViewModel.cancelClubApply.observe(this) { status ->
            detailViewModel.refreshDetailInfo(detailViewModel.result.value!!.id)
            when (status) {
                Event.Success -> {
                    BaseModal("성공", "동아리 신청을 취소했습니다.", requireContext()).show()
                }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다. 다시 로그인해주세요.",
                        requireContext()
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(requireActivity(), IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", requireContext()).show()
                }
                else -> {
                    BaseModal("오류", "알 수 없는 오류 발생, 개발자에게 문의해주세요.", requireContext()).show()
                }
            }
        }
    }

    private fun observeOpenClubApplyEvent() {
        clubViewModel.openingClubApplication.observe(this) { status ->
            detailViewModel.refreshDetailInfo(detailViewModel.result.value!!.id)
            when (status) {
                Event.Success -> {
                    BaseModal("성공", "동아리 신청을 오픈했습니다.", requireContext()).show()
                }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다. 다시 로그인해주세요.",
                        requireContext()
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(requireActivity(), IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.ForBidden -> {
                    BaseModal("실패", "부장만이 할수있는 행동입니다.", requireContext()).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", requireContext()).show()
                }
                else -> {
                    BaseModal("오류", "알수 없는 오류 발생, 개발자에게 문의해주세요.", requireContext()).show()
                }
            }
        }
    }

    private fun observeCloseClubApplyEvent() {
        clubViewModel.closingClubApplication.observe(this) { status ->
            detailViewModel.refreshDetailInfo(detailViewModel.result.value!!.id)
            when (status) {
                Event.Success -> {
                    BaseModal("성공", "동아리 신청을 마감했습니다.", requireContext()).show()
                }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다. 다시 로그인해주세요.",
                        requireContext()
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(requireActivity(), IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.ForBidden -> {
                    BaseModal("실패", "부장만이 할수있는 행동입니다.", requireContext()).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", requireContext()).show()
                }
                else -> {
                    BaseModal("오류", "알수 없는 오류 발생, 개발자에게 문의해주세요.", requireContext()).show()
                }
            }
        }
    }

    private fun observeExitClubEvent() {
        clubViewModel.exitClubStatus.observe(this) { status ->
            when (status) {
                Event.Success -> {
                    BaseModal("성공", "동아리를 탈퇴하였습니다.", requireContext()).show()
                }
                Event.Unauthorized -> {
                    BaseModal("오류", "토큰이 만료되었습니다, 다시 로그인해주세요.", requireContext()).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", requireContext()).show()
                }
                Event.Server -> {
                    BaseModal("오류", "서버에 일시적인 오류가 발생하였습니다. \n 잠시후에 다시 시도해주세요", requireContext()).show()
                }
                else -> {
                    BaseModal("성공", "동아리를 탈퇴하였습니다.", requireContext()).show()
                }
            }
        }
    }

    private fun observeResult() {
        detailViewModel.result.observe(this) {
            showInfo()
            checkRole()
        }
    }

    private fun observeClubDetailInfo() {
        detailViewModel.getClubDetail.observe(this) {
            when (it) {
                Event.Success -> {
                    with(binding.infoLoadingView) {
                        if (isShimmerStarted) {
                            stopShimmer()
                            binding.infoLoadingScroll.visibility = View.GONE
                            binding.scroll.visibility = View.VISIBLE
                        }
                    }
                    with(binding.bannerLoadingView) {
                        if (isShimmerStarted) {
                            stopShimmer()
                            isVisible = false
                        }
                    }
                }
                Event.BadRequest -> {
                    shortToast("동아리 정보를 불러오지 못했습니다.")
                }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다. 다시 로그인해주세요.",
                        requireContext()
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(requireActivity(), IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.UnKnown -> {
                    BaseModal("오류", "알수 없는 오류 발생, 개발자에게 문의해주세요", requireContext()).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (detailViewModel.isProfile.value == true) {
            val intent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(intent)
        } else {
            detailViewModel.setNav(true)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_club, ClubFragment()).commit()
        }

        detailViewModel.setIsProfile(false)
    }
}