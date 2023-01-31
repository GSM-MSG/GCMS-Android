package com.msg.gcms.presentation.component.club.detail

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.msg.gcms.R
import com.msg.gcms.data.local.entity.DetailPageSideBar
import com.msg.gcms.data.local.entity.PromotionPicType
import com.msg.gcms.data.remote.dto.club.response.MemberSummaryResponse
import com.msg.gcms.data.remote.dto.user.response.UserData
import com.msg.gcms.databinding.FragmentDetailBinding
import com.msg.gcms.presentation.adapter.DetailMemberAdapter
import com.msg.gcms.presentation.adapter.DetailPhotoAdapter
import com.msg.gcms.presentation.adapter.DetailSideBarAdapter
import com.msg.gcms.presentation.base.BaseDialog
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.base.BaseModal
import com.msg.gcms.presentation.component.club.ClubFragment
import com.msg.gcms.presentation.component.editclub.EditClubActivity
import com.msg.gcms.presentation.component.main.MainActivity
import com.msg.gcms.presentation.component.member_manage.MemberManageActivity
import com.msg.gcms.presentation.component.profile.ProfileActivity
import com.msg.gcms.presentation.utils.ItemDecorator
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
    var membersList = mutableListOf<MemberSummaryResponse>()
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

    override fun init() {
        observeEvent()
        clickEvent()
        viewSet()
        binding.detail = this
    }

    private fun observeEvent() {
        observeResult()
    }

    private fun clickEvent() {
        clickBackBtn()
        clickSubmitBtn()
    }

    private fun viewSet() {
        detailViewModel.setNav(false)
        settingRecyclerView()
        controllShimmer(true)
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
                controllShimmer(false)
                clubViewModel.stopLottie()
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
            val intent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(intent)
        } else {
            detailViewModel.setNav(true)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_club, ClubFragment()).commit()
        }
        detailViewModel.setIsProfile(false)
    }

    private fun clubMemberRecycler(member: List<UserData>) {
        membersList.clear()
        for (i in member.indices) {
            try {
                membersList.add(
                    MemberSummaryResponse(
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
        // Todo(KimHs) 여기는 뭘처리하길래 result를 넘기고 안씀? - LeeHyeonbin
        detailViewModel.result.value!!.club.let { result ->
            binding.submitBtn.setOnClickListener {
                observeStatus()
                changeDialog()
            }
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
                                        detailViewModel.result.value!!.club.title,
                                        detailViewModel.result.value!!.club.type
                                    )
                                    dialog.dismiss()
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_club, DetailFragment()).commit()
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
                                "query",
                                "${detailViewModel.result.value!!.club.title} + ${detailViewModel.result.value!!.club.type}"
                            )
                            startActivity(intent)
                        }
                        2 -> {
                            BaseDialog("동아리 삭제", "정말 삭제할꺼에요??", context!!).let { dialog ->
                                dialog.show()
                                dialog.dialogBinding.ok.setOnClickListener {
                                    clubViewModel.deleteClub(
                                        detailViewModel.result.value!!.club.title,
                                        detailViewModel.result.value!!.club.type
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
        intent.putExtra("name", detailViewModel.result.value!!.club.title)
        intent.putExtra("type", detailViewModel.result.value!!.club.type)
        intent.putExtra("role", detailViewModel.result.value!!.scope)
        startActivity(intent)
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
                    if (detailViewModel.result.value!!.club.isOpened) {
                        detailViewModel.result.value!!.isApplied.let { applied ->
                            it.text =
                                getString(if (applied) R.string.club_application_cancle else R.string.club_application)
                            it.setBackgroundColor(resources.getColor(if (applied) R.color.pink else R.color.dark_blue))
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
                detailViewModel.result.value!!.club.isOpened.let { open ->
                    BaseDialog(
                        getString(if (open) R.string.deadline else R.string.open),
                        getString(if (open) R.string.ask_dead_club_application else R.string.ask_open_club_application),
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
            "USER" -> {
                detailViewModel.result.value!!.club.let { result ->
                    detailViewModel.result.value!!.isApplied.let { applied ->
                        BaseDialog(
                            getString(if (applied) R.string.cancel else R.string.application),
                            getString(
                                if (applied) R.string.ask_cancel_application else R.string.ask_application_club,
                                result.title
                            ),
                            requireContext()
                        ).let { dialog ->
                            dialog.show()
                            dialog.dialogBinding.ok.setOnClickListener {
                                if (applied) {
                                    clubViewModel.postClubCancel(
                                        result.type,
                                        result.title
                                    )
                                } else
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
    }

    private fun observeStatus() {
        clubViewModel.getClubStatus.observe(this) { status ->
            detailViewModel.result.value!!.club.let {
                detailViewModel.getDetail(it.type, it.title)
            }
            when (status) {
                Event.Success -> {}
                Event.Unauthorized -> {BaseModal("오류","토큰이 만료되었습니다, 앱 종료후 다시 실행해 주세요",requireContext()).show()}
                Event.ForBidden -> {BaseModal("실패","부장만이 할수있는 행동입니다.",requireContext()).show()}
                Event.Conflict -> {BaseModal("실패","이미 다른 동아리에 소속 또는 신청중인 사람입니다.",requireContext()).show()}
                Event.UnKnown-> {BaseModal("오류","알수 없는 오류 발생, 개발자에게 문의해주세요",requireContext()).show()}
                Event.Server -> {}
            }
        }
    }

    private fun controllShimmer(loading: Boolean) {
        with(binding) {
            if (loading) {
                clubBanner.visibility = View.GONE
            } else {
                clubBanner.visibility = View.VISIBLE
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