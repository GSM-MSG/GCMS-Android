package com.msg.gcms.presentation.view.club

import android.content.Context
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentFreeBinding
import com.msg.gcms.presentation.adapter.club_list.ClubListAdapter
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.base.BaseModal
import com.msg.gcms.presentation.utils.enterActivity
import com.msg.gcms.presentation.utils.enterFragment
import com.msg.gcms.presentation.utils.start
import com.msg.gcms.presentation.utils.stop
import com.msg.gcms.presentation.view.club.detail.DetailFragment
import com.msg.gcms.presentation.view.intro.IntroActivity
import com.msg.gcms.presentation.viewmodel.ClubDetailViewModel
import com.msg.gcms.presentation.viewmodel.MainViewModel
import com.msg.gcms.presentation.viewmodel.util.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FreeFragment : BaseFragment<FragmentFreeBinding>(R.layout.fragment_free) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val detailViewModel by activityViewModels<ClubDetailViewModel>()
    private lateinit var adapter: ClubListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel.setIsHeader(true)
    }

    override fun observeEvent() {
        observeClubData()
        observeClubInfo()
    }

    override fun initView() {
        getClubInfo()
        recyclerview()
    }

    private fun getClubInfo() {
        binding.clubLoadingView.start()
        mainViewModel.getClubList()
    }

    private fun recyclerview() {
        binding.clubRecyclerView.layoutManager = GridLayoutManager(context, 2)
    }

    private fun observeClubData() {
        mainViewModel.clubData.observe(this) {
            adapter = ClubListAdapter(mainViewModel.clubData.value)
            adapter.setItemOnClickListener(object : ClubListAdapter.OnItemClickListener {
                override fun onClick(position: Int) {
                    mainViewModel.setIsHeader(false)
                    detailViewModel.getDetail(
                        mainViewModel.clubData.value?.get(position)!!.id
                    )
                    enterFragment(requireActivity(), R.id.fragment_club, DetailFragment())
                }
            })
            binding.clubRecyclerView.adapter = adapter
        }
    }

    private fun observeClubInfo() {
        mainViewModel.getClubList.observe(this) {
            when (it) {
                Event.Success -> {
                    binding.clubLoadingView.stop()
                }
                Event.Loading -> {
                    Log.d("TAG", "observeClubInfo: $it")
                }
                Event.Unauthorized -> {
                    BaseModal(
                        "오류",
                        "토큰이 만료되었습니다, 로그아웃 이후 다시 로그인해주세요.",
                        requireContext()
                    ).let { dialog ->
                        dialog.show()
                        dialog.dialogBinding.ok.setOnClickListener {
                            enterActivity(requireActivity(), IntroActivity())
                            dialog.dismiss()
                        }
                    }
                }
                else -> {
                    BaseModal("오류", "정보를 불러오는 중에\n 오류가 발생하였습니다.", requireContext()).show()
                }
            }
        }
    }
}