package com.msg.gcms.ui.component.club.detail

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.msg.gcms.R
import com.msg.gcms.data.local.entity.PromotionPicType
import com.msg.gcms.data.remote.dto.datasource.club.response.UserInfo
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.databinding.FragmentClubDetailBinding
import com.msg.gcms.ui.adapter.ClubActivitysAdapter
import com.msg.gcms.ui.adapter.ClubMemberAdapter
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.ClubDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentClubDetailBinding>(R.layout.fragment_club_detail) {

    private val TAG = "Detail"
    var urlsList = mutableListOf<PromotionPicType>()
    var membersList = mutableListOf<UserData>()
    private val viewmodel by activityViewModels<ClubDetailViewModel>()
    private val activitysAdapter: ClubActivitysAdapter = ClubActivitysAdapter()
    private val memberAdapter: ClubMemberAdapter = ClubMemberAdapter()

    override fun init() {
        clickBackBtn()
        checkRole()
        sunmitLogic()
        showInfo()
    }

    private fun clickBackBtn() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showInfo() {
        viewmodel.result.let { it ->
            it.club.let {
                binding.clubName.text = it.title
                binding.clubBanner.load(it.bannerUrl)
                binding.explainClubTxt.text = it.description
                binding.link.text = it.relatedLink[0].url
                binding.teacher.text = it.teacher
                binding.directoryTxt.text = it.contact
            }
            it.head.let {
                binding.bossImg.load(it.userImg)
                binding.boss.text = it.name
            }
            clubActivityRecycler(it.activityUrls)
            clubMemberRecycler(it.member)
        }
    }

    private fun clubMemberRecycler(member: List<UserInfo>) {
        membersList.clear()
        for (i in member.indices) {
            val memberName = member[i].name
            val memberImg = member[i].userImg.toString()
            val memberEmail = member[i].email
            val memberGrade = member[i].grade
            val membersClass = member[i].`class`
            val memberNum = member[i].num
            try {
                membersList.add(
                    UserData(
                        email = memberEmail,
                        name = memberName,
                        grade = memberGrade,
                        `class` = membersClass,
                        num = memberNum,
                        userImg = memberImg
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
        memberAdapter.submitList(membersList)
    }

    private fun clubActivityRecycler(activityUrls: List<String>) {
        urlsList.clear()
        for (i in activityUrls.indices) {
            val imageUrl = activityUrls[i]
            try {
                urlsList.add(PromotionPicType(promotionUrl = imageUrl))
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
        activitysAdapter.submitList(urlsList)
    }

    private fun checkRole() {
        binding.submitBtn.let {
            when (viewmodel.result.scope) {
                "HEAD" -> {
                    it.text = getString(R.string.close_application)
                    it.setBackgroundColor(resources.getColor(R.color.dark_blue))
                }
                "MEMBER" -> {
                    it.visibility = View.INVISIBLE
                }
                "USER" -> {
                    if (viewmodel.result.isApplied) {
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

    private fun sunmitLogic() {
        binding.submitBtn.setOnClickListener {

        }
    }
}
