package com.msg.gcms.ui.component.club

import android.content.Intent
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubBinding
import com.msg.gcms.ui.adapter.ClubListAdapter
import com.msg.gcms.ui.base.BaseFragment
import com.msg.gcms.ui.base.BaseModal
import com.msg.gcms.ui.component.club.detail.DetailFragment
import com.msg.gcms.ui.component.clubmaker.MakeClubActivity
import com.msg.gcms.ui.component.profile.ProfileActivity
import com.msg.viewmodel.ClubDetailViewModel
import com.msg.viewmodel.ClubViewModel
import com.msg.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubFragment : BaseFragment<FragmentClubBinding>(R.layout.fragment_club) {
    private val TAG = "ClubFragment"
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val detailViewModel by activityViewModels<ClubDetailViewModel>()
    private val clubViewModel by activityViewModels<ClubViewModel>()
    private lateinit var adapter: ClubListAdapter
    override fun init() {
        mainViewModel.getClubList()
        recyclerview()
        clickProfile()
        clickMakeClubBtn()
        clubTxt()
    }

    private fun recyclerview() {
        binding.clubRecyclerView.layoutManager = GridLayoutManager(context, 2)
        mainViewModel.clubData.observe(this) {
            adapter = ClubListAdapter(mainViewModel.clubData.value)
            adapter.setItemOnClickListener(object : ClubListAdapter.OnItemClickListener {
                override fun onClick(position: Int) {
                    clubViewModel.startLottie(requireActivity().supportFragmentManager)
                    detailViewModel.getDetail(
                        mainViewModel.clubData.value?.get(position)!!.type,
                        mainViewModel.clubData.value?.get(position)!!.title
                    )
                    observeStatus()
                }
            })
            binding.clubRecyclerView.adapter = adapter
        }
    }

    private fun clubTxt() {
        mainViewModel.clubName.observe(this) {
            binding.clubNameTxt.text = mainViewModel.clubName.value
            mainViewModel.getClubList()
        }
    }

    private fun clickProfile() {
        binding.profileBtn.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }

    private fun clickMakeClubBtn() {
        binding.addClubBtn.setOnClickListener {
            val intent = Intent(activity, MakeClubActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }

    private fun observeStatus() {
        detailViewModel.clearResult()
        detailViewModel.result.observe(this) {
            if (it != null) {
                when (detailViewModel.getDetailStatus.value) {
                    in 200..299 -> {
                        Log.d(TAG, "GetDetail : Status - ${detailViewModel.getDetailStatus.value}")
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_club, DetailFragment()).commit()
                    }
                    else -> {
                        shortToast("동아리 정보를 불러오지 못했습니다.")
                        Log.d(
                            TAG,
                            "GetDetail : Error Status - ${detailViewModel.getDetailStatus.value}"
                        )
                    }
                }
            }
        }

        clubViewModel.getClubStatus.observe(this) {
            when (it) {
                in 200..299 -> {}
                401 -> {
                    BaseModal("오류", "토큰이 만료되었습니다, 앱 종료후 다시 실행해 주세요", requireContext()).show()
                }
                else -> {
                    BaseModal("오류", "알수 없는 오류 발생, 개발자에게 문의해주세요", requireContext()).show()
                }
            }
        }
    }
}