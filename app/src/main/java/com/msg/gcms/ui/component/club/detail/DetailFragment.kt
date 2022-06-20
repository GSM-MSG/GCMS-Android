package com.msg.gcms.ui.component.club.detail

import android.content.Context
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
import com.msg.gcms.databinding.DetailDialogBinding
import com.msg.gcms.databinding.FragmentDetailBinding
import com.msg.gcms.ui.adapter.DetailMemberAdapter
import com.msg.gcms.ui.adapter.DetailPhotoAdapter
import com.msg.gcms.ui.base.BaseDialog
import com.msg.gcms.ui.base.BaseFragment
import com.msg.gcms.ui.component.club.ClubFragment
import com.msg.viewmodel.ClubDetailViewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val TAG = "DetailFragment"
    private val detailViewModel by activityViewModels<ClubDetailViewModel>()
    private lateinit var dialogBinding: DetailDialogBinding
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
        Log.d(TAG,photo.toString())
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
        }

        with(binding.promotionClubImg) {
            layoutManager = GridLayoutManager(
                context,
                2,
            )
        }
    }

    private fun clickSubmitBtn(context: Context) {
        binding.submitBtn.setOnClickListener {
            val dialog = BaseDialog(context, R.layout.detail_dialog)
            changeDialog(dialog)
            dialog.showDialog()
        }
    }

    private fun checkRole() {
        binding.submitBtn.let {
            when (detailViewModel.result.value!!.scope) {
                "HEAD" -> {
                    it.text = getString(R.string.close_application)
                    it.setBackgroundColor(resources.getColor(R.color.dark_blue))
                }
                "MEMBER" -> {
                    it.visibility = View.INVISIBLE
                }
                "USER" -> {
                    if (detailViewModel.result.value!!.isApplied) {
                        it.text = getString(R.string.club_application_cancle)
                        it.setBackgroundColor(resources.getColor(R.color.pink))
                    } else {
                        it.text = getString(R.string.club_application)
                        it.setBackgroundColor(resources.getColor(R.color.dark_blue))
                    }
                }
            }
        }
    }

    private fun changeDialog(dialog: BaseDialog) {
        when (detailViewModel.result.value!!.scope) {
            "HEAD" -> {
                with(dialogBinding) {
                    dialogTitle.text = getString(R.string.deadline)
                    dialogMsg.text = getString(R.string.ask_dead_club_application)
                    ok.setOnClickListener {
                        deadline()
                    }
                    cancel.setOnClickListener {
                        dialog.dismiss()
                    }
                }
            }
            "MEMBER" -> {
            }
            "USER" -> {
                with(dialogBinding) {
                    if (detailViewModel.result.value!!.isApplied) {
                        dialogTitle.text = getString(R.string.application_cancel)
                        dialogMsg.text = getString(R.string.ask_cancel_application)
                        ok.setOnClickListener {
                            cancelApplication()
                        }
                        cancel.setOnClickListener {
                            dialog.dismiss()
                        }
                    } else {
                        dialogTitle.text = getString(R.string.application)
                        dialogMsg.text =
                            getString(
                                R.string.can_you_application_club,
                                detailViewModel.result.value!!.club.title
                            )
                        ok.setOnClickListener {
                            application()
                        }
                        cancel.setOnClickListener {
                            dialog.dismiss()
                        }
                    }
                }
            }
        }
    }

    private fun application() {
    }

    private fun cancelApplication() {
    }

    private fun deadline() {
    }
}