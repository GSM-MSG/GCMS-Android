package com.msg.gcms.ui.component.member_manage

import android.animation.ObjectAnimator
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.msg.gcms.R
import com.msg.gcms.databinding.ActivityMemberManagementBinding
import com.msg.gcms.ui.adapter.ApplicantAdapter
import com.msg.gcms.ui.adapter.MemberAdapter
import com.msg.gcms.ui.base.BaseActivity
import com.msg.gcms.ui.base.BaseDialog
import com.msg.viewmodel.MemberManageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberMangeActivity :
    BaseActivity<ActivityMemberManagementBinding>(R.layout.activity_member_management) {
    private val viewModel by viewModels<MemberManageViewModel>()
    lateinit var memberAdapter: MemberAdapter
    lateinit var applicantAdapter: ApplicantAdapter
    override fun observeEvent() {
        viewModel.memberList.observe(this) {
            settingRecyclerView()
        }
        viewModel.applicantList.observe(this) {
            settingRecyclerView2()
        }
    }

    override fun viewSetting() {
        clickExpandable()
    }

    private fun settingRecyclerView() {
        memberAdapter = MemberAdapter(viewModel.memberList.value!!)
        memberAdapter.setItemOnClickListener(object : MemberAdapter.OnItemClickListener {
            override fun mandate(position: Int) {
                BaseDialog("위임", "부장 권한이 이동됩니다", this@MemberMangeActivity).let {
                    it.show()
                    it.dialogBinding.ok.setOnClickListener {
                        viewModel.delegate(viewModel.memberList.value!!.get(position).email)
                    }
                }
            }

            override fun kick(position: Int) {
                BaseDialog(
                    "강퇴",
                    "${viewModel.memberList.value!!.get(position).name}님을 강퇴하시겠습니까?",
                    this@MemberMangeActivity
                ).let {
                    it.show()
                    it.dialogBinding.ok.setOnClickListener {
                        viewModel.kickUser(viewModel.memberList.value!!.get(position).email)
                    }
                }
            }
        })
        binding.memberList.layoutManager = LinearLayoutManager(this)
        binding.memberList.adapter = memberAdapter
    }

    private fun settingRecyclerView2() {
        applicantAdapter = ApplicantAdapter(viewModel.applicantList.value!!)
        applicantAdapter.setOnClickListener(object : ApplicantAdapter.onClickListener {
            override fun accept(position: Int) {
                BaseDialog(
                    "승인",
                    "동료가 되었다!!",
                    this@MemberMangeActivity
                ).let {
                    it.show()
                    it.dialogBinding.ok.setOnClickListener {
                        viewModel.accept(viewModel.memberList.value!!.get(position).email)
                    }
                }
            }

            override fun reject(position: Int) {
                BaseDialog(
                    "거절",
                    "바2",
                    this@MemberMangeActivity
                ).let {
                    it.show()
                    it.dialogBinding.ok.setOnClickListener {
                        viewModel.reject(viewModel.memberList.value!!.get(position).email)
                    }
                }
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
}