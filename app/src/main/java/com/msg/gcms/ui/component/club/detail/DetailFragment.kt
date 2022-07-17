package com.msg.gcms.ui.component.club.detail

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
import com.msg.gcms.data.remote.dto.datasource.club.response.MemberSummaryResponse
import com.msg.gcms.data.remote.dto.datasource.club.response.UserInfo
import com.msg.gcms.databinding.FragmentDetailBinding
import com.msg.gcms.ui.adapter.DetailMemberAdapter
import com.msg.gcms.ui.adapter.DetailPhotoAdapter
import com.msg.gcms.ui.adapter.DetailSideBarAdapter
import com.msg.gcms.ui.base.BaseDialog
import com.msg.gcms.ui.base.BaseFragment
import com.msg.gcms.ui.component.club.ClubFragment
import com.msg.gcms.ui.component.editclub.EditClubActivity
import com.msg.gcms.ui.component.main.MainActivity
import com.msg.gcms.utils.ItemDecorator
import com.msg.viewmodel.ClubDetailViewModel
import com.msg.viewmodel.ClubViewModel
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
        sideBarAdapter.setItemOnClickListener(object: DetailSideBarAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                if(sideBarAdapter.itemCount == 2) {
                    when (position) {
                        0 -> {
                            shortToast("유저 보기")
                        }
                        1 -> {
                            shortToast("동아리 탈퇴")
                        }
                    }
                } else {
                    when(position) {
                        0 -> {
                            shortToast("유저 관리")
                        }
                        1 -> {
                            val intent = Intent(context, EditClubActivity::class.java)
                            intent.putExtra("query", "${detailViewModel.result.value!!.club.title} + ${detailViewModel.result.value!!.club.type}")
                            startActivity(intent)
                        }
                        2 -> {
                            shortToast("동아리 삭제")
                        }
                    }
                }
            }
        })
        binding.sideBarRv.adapter = sideBarAdapter
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
                in 200..299 -> {}
                401 -> shortToast("토큰이 만료되었습니다, 다시 로그인 해주세요")
                403 -> shortToast("권한이 없습니다.")
                404 -> shortToast("존재하지 않는 동아리 입니다.")
                409 -> shortToast("이미 다른 동아리에 신청 혹은 소속되어있습니다.")
                else -> shortToast("알수 없는 오류.")
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
        detailViewModel.setNav(true)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_club, ClubFragment()).commit()
    }
}