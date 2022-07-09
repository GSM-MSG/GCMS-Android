package com.msg.gcms.ui.component.club.detail

import android.util.Log
import android.view.View
import androidx.core.net.toUri
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
import com.msg.gcms.ui.component.main.MainActivity
import com.msg.gcms.utils.ItemDecorator
import com.msg.viewmodel.ClubDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail),
    MainActivity.OnBackPressedListener {

    private val TAG = "DetailFragment"
    private val detailViewModel by activityViewModels<ClubDetailViewModel>()
    var membersList = mutableListOf<MemberSummaryResponse>()
    var activityUrlsList = mutableListOf<ActivityPhotoType>()
    private val detailMemberAdapter = DetailMemberAdapter()
    private val detailPhotoAdapter = DetailPhotoAdapter()

    override fun init() {
        detailViewModel.setNav(false)
        observeEvent()
        settingRecyclerView()
        clickBackBtn()
    }

    private fun observeEvent() {
        observeResult()
    }

    private fun observeResult() {
        detailViewModel.result.observe(this) {
            showInfo()
        }
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
        checkRole()
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
        Log.d(TAG, photo.toString())
        activityUrlsList.clear()
        for (i in photo.indices) {
            try {
                activityUrlsList.add(
                    ActivityPhotoType(
                        activityPhoto = photo[i].toUri()
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

    fun sideBarBtn(view: View) {
        when (detailViewModel.result.value?.scope) {
            "HEAD" -> {
            }
            "MEMBER" -> {
            }
            else -> {
                shortToast("권한이 없습니다.")
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
                            R.string.open_application
                        } else R.string.close_application
                    )
                }
                "MEMBER" -> {
                    it.visibility = View.INVISIBLE
                }
                "OTHER" -> {
                    it.visibility = View.INVISIBLE
                    binding.sideBarBtn.visibility = View.GONE
                }
                "USER" -> {
                    if (detailViewModel.result.value!!.club.isOpened) {
                        it.text = getString(
                            if (detailViewModel.result.value!!.isApplied) {
                                R.string.club_application_cancle
                            } else R.string.club_application
                        )
                        it.setBackgroundColor(
                            resources.getColor(
                                if (detailViewModel.result.value!!.isApplied) {
                                    R.color.pink
                                } else R.color.dark_blue
                            )
                        )
                    } else {
                        it.visibility = View.INVISIBLE
                    }
                    binding.sideBarBtn.visibility = View.GONE
                }
            }
        }
    }

    override fun onBackPressed() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_club, ClubFragment()).commit()
        detailViewModel.setNav(true)
    }
}