package com.msg.gcms.presentation.view.member_manage

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityMemberManagementBinding
import com.msg.gcms.presentation.adapter.applicant.ApplicantAdapter
import com.msg.gcms.presentation.adapter.member.MemberAdapter
import com.msg.gcms.presentation.base.BaseActivity
import com.msg.gcms.presentation.base.BaseDialog
import com.msg.gcms.presentation.base.BaseModal
import com.msg.gcms.presentation.utils.enterActivity
import com.msg.gcms.presentation.view.intro.IntroActivity
import com.msg.gcms.presentation.viewmodel.MemberManageViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberManageActivity :
    BaseActivity<ActivityMemberManagementBinding>(R.layout.activity_member_management) {

    private val viewModel by viewModels<MemberManageViewModel>()
    private lateinit var memberAdapter: MemberAdapter
    private lateinit var applicantAdapter: ApplicantAdapter

    override fun observeEvent() {
        observeMemberList()
        observeApplicantList()
        observeMemberStatus()
        observeApplicantStatus()
    }

    private fun observeMemberList() {
        viewModel.memberList.observe(this) {
            settingMemberAdapter()
        }
    }

    private fun observeApplicantList() {
        viewModel.applicantList.observe(this) {
            settingApplicantAdapter()
        }
    }

    override fun viewSetting() {
        viewModel.setClubId(intent.getLongExtra("clubId", 0))
        clickExpandable()
        clickBackBtn()
        viewTitle()
    }

    private fun viewTitle() {
        if (intent.getStringExtra("role").equals("MEMBER")) binding.title.text = "동아리 멤버 확인하기"
    }

    private fun showDialog(title: String, msg: String, context: Context, action: () -> Unit) {
        BaseDialog(title, msg, context).let { dialog ->
            dialog.show()
            dialog.dialogBinding.ok.setOnClickListener {
                action()
                viewModel.getMember()
                viewModel.getApplicant()
                dialog.dismiss()
            }
        }
    }

    private fun settingMemberAdapter() {
        memberAdapter = MemberAdapter(viewModel.memberList.value!!, intent.getStringExtra("role")!!)
        memberAdapter.setItemOnClickListener(object : MemberAdapter.OnItemClickListener {
            override fun mandate(position: Int) {
                showDialog(
                    "경고",
                    "정말 부장 권한을 ${viewModel.memberList.value!![position].name}님에게 \n위임하시겠습니까?",
                    this@MemberManageActivity
                ) {
                    viewModel.delegate(viewModel.memberList.value!![position].uuid)
                    observeDelegateStatus()
                }
            }

            override fun kick(position: Int) {
                showDialog(
                    "강퇴",
                    "${viewModel.memberList.value!![position].name}님을 강퇴하시겠습니까?",
                    this@MemberManageActivity
                ) {
                    viewModel.kickUser(viewModel.memberList.value!![position].uuid)
                    observeKickUserStatus()
                }
            }
        })
        binding.memberList.layoutManager = LinearLayoutManager(this)
        binding.memberList.adapter = memberAdapter
    }

    private fun settingApplicantAdapter() {
        applicantAdapter =
            ApplicantAdapter(viewModel.applicantList.value!!, intent.getStringExtra("role")!!)
        applicantAdapter.setOnClickListener(object : ApplicantAdapter.onClickListener {
            override fun accept(position: Int) {
                showDialog(
                    "승인",
                    "${viewModel.applicantList.value!![position].name}님의 동아리 신청을\n승인하시겠습니까?",
                    this@MemberManageActivity
                ) {
                    viewModel.accept(viewModel.applicantList.value!![position].uuid)
                    observeAcceptApplicantStatus()
                }
            }

            override fun reject(position: Int) {
                showDialog(
                    "거절",
                    "${viewModel.applicantList.value!![position].name}님의 동아리 신청을\n거절하시겠습니까?",
                    this@MemberManageActivity
                ) {
                    viewModel.reject(viewModel.applicantList.value!![position].uuid)
                    observeRejectApplicantStatus()
                }
            }
        })
        binding.applicantList.layoutManager = LinearLayoutManager(this)
        binding.applicantList.adapter = applicantAdapter
    }

    private fun clickExpandable() = with(binding) {
        listOf(memberListBtn, memberListLayout).forEach {
            it.setOnClickListener {
                if (memberList.visibility == View.GONE) viewModel.getMember()
                expandableAnim(memberListBtn, memberList.visibility, memberList)
            }
        }
        listOf(applicantListBtn, applicantListLayout).forEach {
            it.setOnClickListener {
                if (memberList.visibility == View.GONE) viewModel.getApplicant()
                expandableAnim(applicantListBtn, applicantList.visibility, applicantList)
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

    private fun clickBackBtn() {
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun observeMemberStatus() {
        viewModel.getMemberListState.observe(this) {
            when (it) {
                Event.Success -> {
                }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다, 로그아웃 이후 다시 로그인해주세요.",
                        this
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(this, IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.ForBidden -> {
                    BaseModal("실패", "동아리 구성원만이 맴버 정보를 확인할 수 있습니다.", this).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", this).show()
                }
                Event.Server -> {
                    BaseModal("오류", "서버에서 문제가 발생하였습니다.", this).show()
                }
                else -> {
                    BaseModal("오류", "알 수 없는 오류 발생, 개발자에게 문의해주세요", this).show()
                }
            }
        }
    }

    private fun observeApplicantStatus() {
        viewModel.getApplicantListState.observe(this) {
            when (it) {
                Event.Success -> {
                }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다, 로그아웃 이후 다시 로그인해주세요.",
                        this
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(this, IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", this).show()
                }
                Event.Server -> {
                    BaseModal("오류", "서버에서 문제가 발생하였습니다.", this).show()
                }
                else -> {
                    BaseModal("오류", "알 수 없는 오류 발생, 개발자에게 문의해주세요", this).show()
                }
            }
        }
    }

    private fun observeKickUserStatus() {
        viewModel.kickUserState.observe(this) {
            when (it) {
                Event.Success -> {
                    BaseModal("성공", "강퇴를 완료하였습니다.", this).show()
                }
                Event.BadRequest -> {
                    BaseModal("실패", "자기 자신을 강퇴할 수 없습니다.", this).show()
                }
                Event.ForBidden -> {
                    BaseModal("실패", "부장만이 이 행동을 할수 있습니다.", this).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", this).show()
                }
                Event.Server -> {
                    BaseModal("오류", "서버에서 문제가 발생하였습니다.", this).show()
                }
                else -> {
                    BaseModal("오류", "알 수 없는 오류 발생, 개발자에게 문의해주세요.", this).show()
                }
            }
        }
    }

    private fun observeDelegateStatus() {
        viewModel.delegateState.observe(this) {
            when (it) {
                Event.Success -> {
                    BaseModal("성공", "권한 위임을 완료했습니다.", this).show()
                }
                Event.BadRequest -> {
                    BaseModal("실패", "부장 자신에겐 권한을 위임할 수 없습니다.", this).show()
                }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다, 로그아웃 이후 다시 로그인해주세요.",
                        this
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(this, IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.ForBidden -> {
                    BaseModal("실패", "부장만이 권한 위임을 진행할 수 있습니다.", this).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", this).show()
                }
                Event.Server -> {
                    BaseModal("오류", "서버에서 문제가 발생하였습니다.", this).show()
                }
                else -> {
                    BaseModal("오류", "알 수 없는 오류 발생, 개발자에게 문의해주세요.", this).show()
                }
            }
        }
    }

    private fun observeAcceptApplicantStatus() {
        viewModel.acceptApplicantState.observe(this) {
            when (it) {
                Event.Success -> {
                    BaseModal("완료", "동아리 신청을 승인했습니다.", this).show()
                }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다, 로그아웃 이후 다시 로그인해주세요.",
                        this
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(this, IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.ForBidden -> {
                    BaseModal("실패", "부장만이 이 행동을 할 수 있습니다.", this).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", this).show()
                }
                Event.Server -> {
                    BaseModal("오류", "서버에서 문제가 발생하였습니다.", this).show()
                }
                else -> {
                    BaseModal("오류", "알 수 없는 오류 발생, 개발자에게 문의해주세요.", this).show()
                }
            }
        }
    }

    private fun observeRejectApplicantStatus() {
        viewModel.rejectApplicantState.observe(this) {
            when (it) {
                Event.Success -> {
                    BaseModal("완료", "동아리 신청을 거절했습니다.", this).show()
                }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다, 로그아웃 이후 다시 로그인해주세요.",
                        this
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(this, IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                Event.ForBidden -> {
                    BaseModal("실패", "부장만이 이 행동을 할 수 있습니다.", this).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "해당 동아리, 또는 유저를 찾을 수 없습니다.", this).show()
                }
                Event.Server -> {
                    BaseModal("오류", "서버에서 문제가 발생하였습니다.", this).show()
                }
                else -> {
                    BaseModal("완료", "동아리 신청을 거절했습니다.", this).show()
                }
            }
        }
    }
}