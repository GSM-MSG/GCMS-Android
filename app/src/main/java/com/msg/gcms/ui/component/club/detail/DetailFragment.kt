package com.msg.gcms.ui.component.club.detail

import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import coil.load
import com.msg.gcms.R
import com.msg.gcms.databinding.DetailDialogBinding
import com.msg.gcms.databinding.FragmentDetailBinding
import com.msg.gcms.ui.base.BaseDialog
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.ClubDetailViewModel

class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val TAG = "DetailActivity"
    private val vm by activityViewModels<ClubDetailViewModel>()
    private lateinit var dialogBinding: DetailDialogBinding


    override fun init() {
        showInfo()
        checkRole()
        clickBackBtn()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        clickSubmitBtn(context)
    }

    private fun showInfo() {
        with(binding) {
            vm.result.value!!.let { it ->
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
            }
        }
    }

    private fun clickBackBtn() {
        binding.backBtn.setOnClickListener {

        }
    }

    private fun clickSubmitBtn(context: Context) {
        binding.submitBtn.setOnClickListener {
            Log.d(TAG, "click")
            val dialog = BaseDialog(context, R.layout.detail_dialog)
            changeDialog(dialog)
            dialog.showDialog()
        }
    }

    private fun checkRole() {
        binding.submitBtn.let {
            when (vm.result.value!!.scope) {
                "HEAD" -> {
                    it.text = getString(R.string.close_application)
                    it.setBackgroundColor(resources.getColor(R.color.dark_blue))
                }
                "MEMBER" -> {
                    it.visibility = View.INVISIBLE
                }
                "USER" -> {
                    if (vm.result.value!!.isApplied) {
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
        when (vm.result.value!!.scope) {
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
                    if (vm.result.value!!.isApplied) {
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
                                vm.result.value!!.club.title
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