package com.msg.gcms.ui.component.club.detail

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.msg.gcms.R
import com.msg.gcms.data.local.entity.DetailPageUserInfo
import com.msg.gcms.data.local.entity.PromotionPicType
import com.msg.gcms.data.remote.dto.datasource.club.response.UserInfo
import com.msg.gcms.databinding.FragmentClubDetailBinding
import com.msg.gcms.ui.adapter.ClubActivitysAdapter
import com.msg.gcms.ui.adapter.ClubMemberAdapter
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.ClubDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentClubDetailBinding>(R.layout.fragment_club_detail) {

    private val TAG = "Detail"
    private val viewmodel by activityViewModels<ClubDetailViewModel>()
    var urlsList = mutableListOf<PromotionPicType>()
    var membersList = mutableListOf<DetailPageUserInfo>()
    private val activitysAdapter: ClubActivitysAdapter = ClubActivitysAdapter()
    private val memberAdapter: ClubMemberAdapter = ClubMemberAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding.submitBtn.setOnClickListener {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.detail_dialog)
            dialog.setCancelable(false)

            val dialogTitle = dialog.findViewById<TextView>(R.id.dialog_title)
            val dialogText = dialog.findViewById<TextView>(R.id.dialog_msg)
            val dialogOkBtn = dialog.findViewById<TextView>(R.id.ok)
            val dialogCancleBtn = dialog.findViewById<TextView>(R.id.cancel)

            when (viewmodel.result.scope) {
                "HEAD" -> {
                    dialogTitle.text = getString(R.string.deadline)
                    dialogText.text = getString(R.string.ask_dead_club_application)
                    dialogOkBtn.setOnClickListener {
                        deadline()
                    }
                    dialogCancleBtn.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                "MEMBER" -> {
                }
                "USER" -> {
                    if (viewmodel.result.isApplied) {
                        dialogTitle.text = getString(R.string.application_cancel)
                        dialogText.text = getString(R.string.ask_cancel_application)
                        dialogOkBtn.setOnClickListener {
                            cancelApplication()
                        }
                        dialogCancleBtn.setOnClickListener {
                            dialog.dismiss()
                        }
                    } else {
                        dialogTitle.text = getString(R.string.application)
                        dialogText.text =  getString(R.string.can_you_application_club, viewmodel.result.club.title)
                        dialogOkBtn.setOnClickListener {
                            application()
                        }
                        dialogCancleBtn.setOnClickListener {
                            dialog.dismiss()
                        }
                    }
                }
            }
        }
    }

    override fun init() {
        clickBackBtn()
        checkRole()
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
                binding.link.text = it.notionLink
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
