package com.msg.gcms.ui.component.club.detail

import android.app.Dialog
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import coil.load
import com.msg.gcms.R
import com.msg.gcms.data.local.entity.DetailPageUserInfo
import com.msg.gcms.data.local.entity.PromotionPicType
import com.msg.gcms.data.remote.dto.datasource.club.response.UserInfo
import com.msg.gcms.databinding.ActivityDetailBinding
import com.msg.gcms.databinding.DetailDialogBinding
import com.msg.gcms.ui.adapter.ClubActivitysAdapter
import com.msg.gcms.ui.adapter.ClubMemberAdapter
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.base.BaseDialog
import com.msg.gcms.ui.component.main.MainActivity
import com.msg.viewmodel.ClubDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    private val TAG = "Detail"
    private val viewmodel by viewModels<ClubDetailViewModel>()
    var urlsList = mutableListOf<PromotionPicType>()
    var membersList = mutableListOf<DetailPageUserInfo>()
    private val activitysAdapter: ClubActivitysAdapter = ClubActivitysAdapter()
    private val memberAdapter: ClubMemberAdapter = ClubMemberAdapter()
    private lateinit var dialogBinding: DetailDialogBinding

    override fun viewSetting() {
        dialogBinding = DataBindingUtil.setContentView(this, R.layout.detail_dialog)
        clickBackBtn()
        checkRole()
        showInfo()
        viewmodel.getDetail("MAJOR", "Did")
    }

    override fun observeEvent() {
        binding.submitBtn.setOnClickListener {
            val dialog = BaseDialog(this, R.layout.detail_dialog)
            changeDialog(dialog)
        }
    }

    private fun changeDialog(dialog: Dialog) {
        when (viewmodel.result.scope) {
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
                    if (viewmodel.result.isApplied) {
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
                                viewmodel.result.club.title
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

    private fun clickBackBtn() {
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun showInfo() {
        with(binding) {
            viewmodel.result.let { it ->
                it.club.let {
                    clubName.text = it.title
                    clubBanner.load(it.bannerUrl)
                    explainClubTxt.text = it.description
                    link.text = it.notionLink
                    teacher.text = it.teacher
                    directoryTxt.text = it.contact
                }
                it.head.let {
                    bossImg.load(it.userImg)
                    boss.text = it.name
                }
                clubActivityRecycler(it.activityUrls)
                clubMemberRecycler(it.member)
            }
        }
    }

    private fun clubMemberRecycler(member: List<UserInfo>) {
        membersList.clear()
        for (i in member.indices) {
            val memberName = member[i].name
            val memberImg = member[i].userImg.toString()
            try {
                membersList.add(
                    DetailPageUserInfo(
                        name = memberName,
                        imgUrl = memberImg
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

    private fun application() {
    }

    private fun cancelApplication() {
    }

    private fun deadline() {
    }
}