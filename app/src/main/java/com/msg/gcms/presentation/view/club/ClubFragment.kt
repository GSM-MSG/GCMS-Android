package com.msg.gcms.presentation.view.club

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubBinding
import com.msg.gcms.presentation.adapter.ClubListAdapter
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.base.BaseModal
import com.msg.gcms.presentation.view.club.detail.DetailFragment
import com.msg.gcms.presentation.view.clubmaker.MakeClubActivity
import com.msg.gcms.presentation.view.profile.ProfileActivity
import com.msg.gcms.presentation.viewmodel.ClubDetailViewModel
import com.msg.gcms.presentation.viewmodel.ClubViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import com.msg.gcms.presentation.viewmodel.MainViewModel
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

        observeClubDetailInfo()
        // detailViewModel.clearResult()
        /*
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
        }*/
    }


    private fun observeClubDetailInfo() {
        detailViewModel.getClubDetail.observe(this) {
            when(it) {
                Event.Success -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_club, DetailFragment()).commit()
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

    private fun observe() {
        clubViewModel.getClubStatus.observe(this) {
            when (it) {
                Event.Success -> {
                }
                Event.Unauthorized -> {
                    BaseModal("오류", "토큰이 만료되었습니다, 앱 종료후 다시 실행해 주세요", requireContext()).show()
                }
                Event.Server -> {

                }
                Event.UnKnown -> {
                    BaseModal("오류", "알수 없는 오류 발생, 개발자에게 문의해주세요", requireContext()).show()
                }
            }
        }
    }
}