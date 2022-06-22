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
import com.msg.gcms.ui.base.BaseFragment
import com.msg.gcms.ui.component.club.ClubFragment
import com.msg.gcms.utils.ItemDecorator
import com.msg.viewmodel.ClubDetailViewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val TAG = "DetailFragment"
    private val detailViewModel by activityViewModels<ClubDetailViewModel>()
    var membersList = mutableListOf<MemberSummaryResponse>()
    var activityUrlsList = mutableListOf<ActivityPhotoType>()
    private val detailMemberAdapter = DetailMemberAdapter()
    private val detailPhotoAdaper = DetailPhotoAdapter()

    override fun init() {
        detailViewModel.setNav(false)
        detailViewModel.result.observe(this) {
            showInfo()
        }
        settingRecyclerView()
        checkRole()
        clickBackBtn()
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
                clubPromotionImgRecycler(it.activityurls)
            }
        }
    }

    private fun setTeacherInfo(name: String) {
        with(binding) {
            if (name == "") {
                teacherImg.visibility = View.INVISIBLE
                teacherName.visibility = View.INVISIBLE
            } else {
                teacherName.visibility = View.VISIBLE
                teacherImg.visibility = View.VISIBLE
                teacherName.text = name
            }
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
        Log.d(TAG, photo.toString())
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
        binding.submitBtn.setOnClickListener {

        }
    }

    private fun checkRole() {
        binding.submitBtn.let {
            when (detailViewModel.result.value!!.scope) {
                "HEAD" -> {
                    if (detailViewModel.result.value!!.club.isOpened) {
                        it.text = getString(R.string.close_application)
                        it.setBackgroundColor(resources.getColor(R.color.dark_blue))
                    } else {
                        it.text = getString(R.string.open_application)
                        it.setBackgroundColor(resources.getColor(R.color.dark_blue))
                    }
                }
                "MEMBER", "ORTHER" -> {
                    it.visibility = View.INVISIBLE
                }
                "USER" -> {
                    if (detailViewModel.result.value!!.club.isOpened) {
                        if (detailViewModel.result.value!!.isApplied) {
                            it.text = getString(R.string.club_application_cancle)
                            it.setBackgroundColor(resources.getColor(R.color.pink))
                        } else {
                            it.text = getString(R.string.club_application)
                            it.setBackgroundColor(resources.getColor(R.color.dark_blue))
                        }
                    } else {
                        it.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}