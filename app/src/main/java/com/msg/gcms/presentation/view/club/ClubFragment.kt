package com.msg.gcms.presentation.view.club

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubBinding
import com.msg.gcms.presentation.adapter.ClubListAdapter
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.base.BaseModal
import com.msg.gcms.presentation.utils.enterActivity
import com.msg.gcms.presentation.utils.enterFragment
import com.msg.gcms.presentation.view.club.detail.DetailFragment
import com.msg.gcms.presentation.view.clubmaker.MakeClubActivity
import com.msg.gcms.presentation.view.profile.ProfileActivity
import com.msg.gcms.presentation.viewmodel.ClubDetailViewModel
import com.msg.gcms.presentation.viewmodel.ClubViewModel
import com.msg.gcms.presentation.viewmodel.MainViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubFragment : BaseFragment<FragmentClubBinding>(R.layout.fragment_club) {
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
                        mainViewModel.clubData.value?.get(position)!!.id
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
            enterActivity(requireActivity(), ProfileActivity())
        }
    }

    private fun clickMakeClubBtn() {
        binding.addClubBtn.setOnClickListener {
            enterActivity(requireActivity(), MakeClubActivity())
        }
    }

    private fun observeStatus() {
        observeClubDetailInfo()
    }

    private fun observeClubDetailInfo() {
        detailViewModel.getClubDetail.observe(this) {
            when (it) {
                Event.Success -> {
                    enterFragment(requireActivity(), R.id.fragment_club, DetailFragment())
                }
                Event.BadRequest -> {
                    shortToast("동아리 정보를 불러오지 못했습니다.")
                }
                Event.Unauthorized -> {
                    BaseModal("오류", "토큰이 만료되었습니다, 앱 종료후 다시 실행해 주세요", requireContext()).show()
                }
                Event.UnKnown -> {
                    BaseModal("오류", "알수 없는 오류 발생, 개발자에게 문의해주세요", requireContext()).show()
                }
            }
        }
    }
}