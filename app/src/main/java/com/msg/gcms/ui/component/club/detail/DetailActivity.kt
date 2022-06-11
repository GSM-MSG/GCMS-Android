package com.msg.gcms.ui.component.club.detail

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityDetailBinding
import com.msg.gcms.databinding.DetailDialogBinding
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.base.BaseDialog
import com.msg.gcms.ui.component.main.MainActivity
import com.msg.viewmodel.ClubDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    private val TAG = "Detail"
    private val viewmodel by viewModels<ClubDetailViewModel>()
    private lateinit var dialogBinding: DetailDialogBinding

    override fun viewSetting() {
        dialogBinding =
            DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.detail_dialog, null, false)
        testShowInfo()
        testCheckRole()
    }

    override fun observeEvent() {
        clickBackBtn()
        binding.submitBtn.setOnClickListener {
            Log.d(TAG, "click")
            val dialog = BaseDialog(this, R.layout.detail_dialog)
            testChangeDialog(dialog)
            dialog.showDialog()
        }
    }

    private fun testShowInfo() {
        with(binding) {
            clubName.text = "잡탕"
            //clubBanner.load(it.bannerUrl)
            explainClubTxt.text = "혜윰은 ‘생각’이라는 뜻의 우리말로, 같이 생각해서 더 좋은 결과를 이루자는 뜻으로 동아리를 개설하였습니다.\n" +
                "\n" +
                "활동 설명 : 같이혜움은 전공을 연계한 봉사활동(멘토링)부터 공모전까지 아울러 학내 학우들과 서로 소통하고 호흡해\n" +
                "공동으로 선의의 결과물을 만들고 있으며, 도시재생지원센터 도시재생대학 프로그램을 통해 주민들과 함께 호흡하는\n" +
                "활동 또한 참여하고 있습니다."
            link.text = "노션.링크링크링크"
            teacherName.text = "홍길동"
            directoryTxt.text = "이현빈#1111"
            //bossImg.load(it.userImg)
            bossName.text = "이현빈"
        }
    }

    private fun clickBackBtn() {
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun testCheckRole() {
        binding.submitBtn.let {
            when ("USER") {
                "HEAD" -> {
                    it.text = getString(R.string.close_application)
                    it.setBackgroundColor(resources.getColor(R.color.dark_blue))
                }
                "MEMBER" -> {
                    it.visibility = View.INVISIBLE
                }
                "USER" -> {
                    if (false) {
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

    private fun testChangeDialog(dialog: BaseDialog) {
        when ("HEAD") {
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
                    if (true) {
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
                                "MSG"
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