package com.msg.gcms.presentation.view.attend

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityAttendBinding
import com.msg.gcms.presentation.adapter.attend.ClubAttendListAdapter
import com.msg.gcms.presentation.base.BaseActivity
import com.msg.gcms.presentation.base.BaseModal
import com.msg.gcms.presentation.utils.enterActivity
import com.msg.gcms.presentation.view.intro.IntroActivity
import com.msg.gcms.presentation.viewmodel.util.Event
import com.msg.gcms.presentation.viewmodel.AttendViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttendActivity : BaseActivity<ActivityAttendBinding>(R.layout.activity_attend) {

    private val viewModel by viewModels<AttendViewModel>()
    private lateinit var clubAttendListAdapter: ClubAttendListAdapter

    override fun observeEvent() {
        observeAttendList()
        observeAttendState()
        observePatchAttendStatusState()
        observePatchAttendStatusCollectivelyState()
    }

    private fun observeAttendList() {
        viewModel.attendList.observe(this) {
            settingClubAttendListAdapter()
        }
    }

    private fun observeAttendState() {
        viewModel.getAttendListState.observe(this) {
            when (it) {
                Event.Success -> {}
                Event.Unauthorized -> {
                    BaseModal(
                        title = "오류",
                        msg = "토큰이 만료되었습니다, 로그아웃 후 다시 로그인해주세요.",
                        context = this
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(this, IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.ForBidden -> {
                    BaseModal(
                        title = "실패",
                        msg = "동아리 구성원만이 출석 정보를 확인할 수 있습니다",
                        context = this
                    ).show()
                }
                Event.NotFound -> {
                    BaseModal(
                        title = "오류",
                        msg = "동아리를 찾을 수 없습니다.",
                        context = this
                    ).show()
                }
                Event.Server -> {
                    BaseModal(
                        title = "오류",
                        msg = "서버에서 문제가 발생하였습니다.",
                        context = this
                    )
                }
                else -> {
                    BaseModal(
                        title = "오류",
                        msg = "알 수 없는 오류 발생, 개발자에게 문의해주세요.",
                        context = this
                    ).show()
                }
            }
        }
    }

    private fun observePatchAttendStatusState() {
        viewModel.patchAttendStatusState.observe(this) {
            when (it) {
                Event.Success -> {}
                Event.Unauthorized -> {
                    BaseModal(
                        title = "오류",
                        msg = "토큰이 만료되었습니다, 로그아웃 후 다시 로그인해주세요.",
                        context = this
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(this, IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.ForBidden -> {
                    BaseModal(
                        title = "실패",
                        msg = "동아리 부장만이 출석 상태를 변경할 수 있습니다",
                        context = this
                    ).show()
                }
                Event.NotFound -> {
                    BaseModal(
                        title = "오류",
                        msg = "동아리를 찾을 수 없습니다.",
                        context = this
                    ).show()
                }
                Event.Server -> {
                    BaseModal(
                        title = "오류",
                        msg = "서버에서 문제가 발생하였습니다.",
                        context = this
                    )
                }
                else -> {
                    BaseModal(
                        title = "오류",
                        msg = "알 수 없는 오류 발생, 개발자에게 문의해주세요.",
                        context = this
                    ).show()
                }
            }
        }
    }

    private fun observePatchAttendStatusCollectivelyState() {
        viewModel.patchAttendStatusCollectivelyState.observe(this) {
            when (it) {
                Event.Success -> {}
                Event.Unauthorized -> {
                    BaseModal(
                        title = "오류",
                        msg = "토큰이 만료되었습니다, 로그아웃 후 다시 로그인해주세요.",
                        context = this
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(this, IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.ForBidden -> {
                    BaseModal(
                        title = "실패",
                        msg = "동아리 부장만이 출석 상태를 변경할 수 있습니다",
                        context = this
                    ).show()
                }
                Event.NotFound -> {
                    BaseModal(
                        title = "오류",
                        msg = "동아리를 찾을 수 없습니다.",
                        context = this
                    ).show()
                }
                Event.Server -> {
                    BaseModal(
                        title = "오류",
                        msg = "서버에서 문제가 발생하였습니다.",
                        context = this
                    )
                }
                else -> {
                    BaseModal(
                        title = "오류",
                        msg = "알 수 없는 오류 발생, 개발자에게 문의해주세요.",
                        context = this
                    ).show()
                }
            }
        }
    }

    override fun viewSetting() {
        viewModel.clubId.value = intent.getLongExtra("clubId", 0)
        onClickExpandBtn()
        onClickBackBtn()
        onClickAttendBtn()
        onClickSettingBtn()
    }

    private fun onClickExpandBtn() = with(binding) {
        listOf(memberAttendListBtn, attendMemberListLayout).forEach {
            it.setOnClickListener {
                if (memberAttendListBtn.rotation == 0F || memberAttendListBtn.rotation == -90F) {
                    if (attendMemberList.visibility == View.GONE) viewModel.getClubAttendList()
                    expandableAnim(memberAttendListBtn, attendMemberList.visibility, attendMemberList)
                }
            }
        }
    }

    private fun expandableAnim(view: View, visibility: Int, visibleView: View) {
        if (visibility == View.GONE) visibleView.visibility = View.VISIBLE
        else visibleView.visibility = View.GONE
        ObjectAnimator.ofFloat(
            view,
            View.ROTATION,
            view.rotation,
            view.rotation + if (visibility == View.GONE) -90 else 90
        ).setDuration(300).start()
    }

    private fun onClickBackBtn() {
        binding.goBackBtn.setOnClickListener {
            setResult(
                Activity.RESULT_OK,
                Intent().putExtra("clubId", viewModel.clubId.value)
            )
            finish()
        }
    }

    private fun onClickAttendBtn() {
        if (viewModel.selectedStudentList.size == 0) {
            shortToast("학생을 한명이상 선택해 주세요!")
            return
        }
        val modal = AttendModalBottomSheet()
        binding.attendBtn.setOnClickListener {
            modal.show(supportFragmentManager, AttendModalBottomSheet.TAG)
        }
    }

    private fun settingClubAttendListAdapter() {
        clubAttendListAdapter = ClubAttendListAdapter(
            itemList = viewModel.attendList.value!!.users
        )
        clubAttendListAdapter.setItemOnClickListener(object : ClubAttendListAdapter.OnItemClickListener {
            override fun select(position: Int, isChecked: Boolean) {
                if (isChecked) {
                    viewModel.selectedStudentList.add(viewModel.attendList.value!!.users[position].attendanceId)
                    viewModel.selectedStudentName.add(viewModel.attendList.value!!.users[position].name)
                } else {
                    viewModel.selectedStudentList.remove(viewModel.attendList.value!!.users[position].attendanceId)
                    viewModel.selectedStudentName.remove(viewModel.attendList.value!!.users[position].name)
                }
            }
        }
        )
    }

    private fun onClickSettingBtn() {
        binding.schoolPeriod.setOnClickListener {
            longToast("열심히 개발중인 기능이에요! 잠시만 기다려 주세요!")
        }
    }
}