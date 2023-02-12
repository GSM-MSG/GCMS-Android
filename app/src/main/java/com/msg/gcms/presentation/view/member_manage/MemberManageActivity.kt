package com.msg.gcms.presentation.view.member_manage

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityMemberManagementBinding
import com.msg.gcms.presentation.adapter.ApplicantAdapter
import com.msg.gcms.presentation.adapter.MemberAdapter
import com.msg.gcms.presentation.base.BaseActivity
import com.msg.gcms.presentation.base.BaseDialog
import com.msg.gcms.presentation.base.BaseModal
import com.msg.gcms.presentation.viewmodel.MemberManageViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberManageActivity :
    BaseActivity<ActivityMemberManagementBinding>(R.layout.activity_member_management) {

    private val viewModel by viewModels<MemberManageViewModel>()
    lateinit var memberAdapter: MemberAdapter
    lateinit var applicantAdapter: ApplicantAdapter

    override fun observeEvent() {
        observeMemberList()
        observeApplicantList()
        observeMemberStatus()
        observeApplicantStatus()
        observeKickUserStatus()
        observeDelegateStatus()
        observeAcceptApplicantStatus()
        observeRejectApplicantStatus()
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
        viewModel.setClub(intent.getStringExtra("name")!!, intent.getStringExtra("type")!!).let {
            clickExpandable()
        }
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
                    "위임",
                    "권한 넘어감",
                    this@MemberManageActivity
                ) { viewModel.delegate(viewModel.memberList.value!!.get(position).email) }
            }

            override fun kick(position: Int) {
                showDialog(
                    "강퇴",
                    "${viewModel.memberList.value!!.get(position).name}님을 강퇴하시겠습니까?",
                    this@MemberManageActivity
                ) { viewModel.kickUser(viewModel.memberList.value!!.get(position).uuid) }
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
                    "동료가 되었다!!",
                    this@MemberManageActivity
                ) { viewModel.accept(viewModel.applicantList.value!!.get(position).email) }
            }

            override fun reject(position: Int) {
                showDialog(
                    "거절",
                    "바2",
                    this@MemberManageActivity
                ) { viewModel.reject(viewModel.applicantList.value!!.get(position).email) }
            }
        })
        binding.applicantList.layoutManager = LinearLayoutManager(this)
        binding.applicantList.adapter = applicantAdapter
    }

    private fun clickExpandable() = with(binding) {
        memberListBtn.setOnClickListener {
            viewModel.getMember()
            expandableAnim(memberListBtn, memberList.visibility, memberList)
        }
        applicantListBtn.setOnClickListener {
            viewModel.getApplicant()
            expandableAnim(applicantListBtn, applicantList.visibility, applicantList)
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
                Event.Success, Event.NotAcceptable -> {
                    BaseModal("오류", "알수 없는 오류 발생, 개발자에게 문의해주세요", this).show()
                }
                Event.ForBidden -> {
                    BaseModal("실패", "부장만이 이 행동을 할수 있습니다.", this).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", this).show()
                }
                Event.Conflict -> {
                    BaseModal("불가", "이미 현재 동아리 또는 다른 동아리에 소속되어 있습니다.", this).show()
                }
                Event.Server -> {
                }
                else -> {

                }
            }
        }
    }

    private fun observeApplicantStatus() {
        viewModel.getApplicantListState.observe(this) {
            when (it) {
                Event.Success, Event.NotAcceptable -> {
                    BaseModal("오류", "알수 없는 오류 발생, 개발자에게 문의해주세요", this).show()
                }
                Event.ForBidden -> {
                    BaseModal("실패", "부장만이 이 행동을 할수 있습니다.", this).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", this).show()
                }
                Event.Conflict -> {
                    BaseModal("불가", "이미 현재 동아리 또는 다른 동아리에 소속되어 있습니다.", this).show()
                }
                Event.Server -> {
                }
                else -> {

                }
            }
        }
    }

    private fun observeKickUserStatus() {
        viewModel.kickUserState.observe(this) {
            when (it) {
                Event.Success, Event.NotAcceptable -> {
                    BaseModal("오류", "알수 없는 오류 발생, 개발자에게 문의해주세요", this).show()
                }
                Event.ForBidden -> {
                    BaseModal("실패", "부장만이 이 행동을 할수 있습니다.", this).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", this).show()
                }
                Event.Conflict -> {
                    BaseModal("불가", "이미 현재 동아리 또는 다른 동아리에 소속되어 있습니다.", this).show()
                }
                Event.Server -> {
                }
                else -> {

                }
            }
        }
    }

    private fun observeDelegateStatus() {
        viewModel.delegateState.observe(this) {
            when (it) {
                Event.Success, Event.NotAcceptable -> {
                    BaseModal("오류", "알수 없는 오류 발생, 개발자에게 문의해주세요", this).show()
                }
                Event.ForBidden -> {
                    BaseModal("실패", "부장만이 이 행동을 할수 있습니다.", this).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", this).show()
                }
                Event.Conflict -> {
                    BaseModal("불가", "이미 현재 동아리 또는 다른 동아리에 소속되어 있습니다.", this).show()
                }
                Event.Server -> {
                }
                else -> {

                }
            }
        }
    }

    private fun observeAcceptApplicantStatus() {
        viewModel.acceptApplicantState.observe(this) {
            when (it) {
                Event.Success, Event.NotAcceptable -> {
                    BaseModal("오류", "알수 없는 오류 발생, 개발자에게 문의해주세요", this).show()
                }
                Event.ForBidden -> {
                    BaseModal("실패", "부장만이 이 행동을 할수 있습니다.", this).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", this).show()
                }
                Event.Conflict -> {
                    BaseModal("불가", "이미 현재 동아리 또는 다른 동아리에 소속되어 있습니다.", this).show()
                }
                Event.Server -> {
                }
                else -> {

                }
            }
        }
    }

    private fun observeRejectApplicantStatus() {
        viewModel.rejectApplicantState.observe(this) {
            when (it) {
                Event.Success, Event.NotAcceptable -> {
                    BaseModal("오류", "알수 없는 오류 발생, 개발자에게 문의해주세요", this).show()
                }
                Event.ForBidden -> {
                    BaseModal("실패", "부장만이 이 행동을 할수 있습니다.", this).show()
                }
                Event.NotFound -> {
                    BaseModal("오류", "동아리를 찾을 수 없습니다.", this).show()
                }
                Event.Conflict -> {
                    BaseModal("불가", "이미 현재 동아리 또는 다른 동아리에 소속되어 있습니다.", this).show()
                }
                Event.Server -> {
                }
                else -> {

                }
            }
        }
    }
}