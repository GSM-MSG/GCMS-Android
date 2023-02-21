package com.msg.gcms.presentation.view.profile

import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentProfileClubBinding
import com.msg.gcms.domain.data.user.get_my_profile.ProfileClubData
import com.msg.gcms.presentation.adapter.editorial_club.EditorialClubAdapter
import com.msg.gcms.presentation.base.BaseFragment
import com.msg.gcms.presentation.view.main.MainActivity
import com.msg.gcms.presentation.viewmodel.ClubViewModel
import com.msg.gcms.presentation.viewmodel.ProfileViewModel

class ProfileClubFragment :
    BaseFragment<FragmentProfileClubBinding>(R.layout.fragment_profile_club) {
    private val viewModel by activityViewModels<ProfileViewModel>()
    private val clubViewModel by activityViewModels<ClubViewModel>()
    private val privateClubList: ArrayList<ProfileClubData> = ArrayList()
    private lateinit var adapter: EditorialClubAdapter
    override fun init() {
        viewClub()
    }

    private fun viewClub() {
        viewModel.profileData.observe(this) {
            it.clubs.forEach { clubData ->
                when (clubData.type) {
                    "MAJOR" -> {
                        binding.apply {
                            noMajorClubImg.visibility = View.GONE
                            majorClubLayout.visibility = View.VISIBLE
                            majorClubImg.load(clubData.bannerImg) {
                                transformations(RoundedCornersTransformation(9f, 9f, 0f, 0f))
                            }
                            majorClubName.text = clubData.title
                            majorClubImg.setOnClickListener {
                                // Todo 동아리 디테일페이지로 넘어가는 코드
                                // getDetail(clubId = clubData.id)
                            }
                        }
                    }
                    "FREEDOM" -> {
                        binding.apply {
                            noFreedomClubImg.visibility = View.GONE
                            freedomClubLayout.visibility = View.VISIBLE
                            freedomClubImg.load(clubData.bannerImg) {
                                transformations(RoundedCornersTransformation(9f, 9f, 0f, 0f))
                            }
                            freedomClubName.text = clubData.title
                            freedomClubImg.setOnClickListener {
                                // Todo 동아리 디테일페이지로 넘어가는 코드
                                // getDetail(clubData.id)
                            }
                        }
                    }
                    "EDITORIAL" -> {
                        binding.apply {
                            privateClub.visibility = View.VISIBLE
                            privateClubRecyclerview.visibility = View.VISIBLE
                            privateClubList.add(clubData)
                        }
                    }
                }
            }
            setRecyclerView()
        }
    }

    // private fun getDetail(clubId: Long) {
    //     clubViewModel.startLottie(requireActivity().supportFragmentManager)
    // }

    private fun setRecyclerView() {
        adapter = EditorialClubAdapter(privateClubList)
        binding.privateClubRecyclerview.adapter = adapter
        binding.privateClubRecyclerview.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(HorizontalItemDecorator(20))
        }
        adapter.setItemOnClickListener(object : EditorialClubAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                Log.d("WWWW","클릭")
                profilePageToDetailPage(privateClubList[position].id)
            }
        })
    }

    inner class HorizontalItemDecorator(private val divHeight: Int) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            when (parent.getChildLayoutPosition(view) % 2) {
                0 -> outRect.right = divHeight
                1 -> outRect.left = divHeight
            }
            outRect.bottom = divHeight * 2
        }
    }

    private fun profilePageToDetailPage(clubId: Long) {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.putExtra("isProfile", true)
        intent.putExtra("clubId", clubId)
        startActivity(intent)
    }

    // private fun observeClubInfoForProfilePageToDetailPage() {
    //     detailViewModel.clearResult()
    //
    //     //code 로 하나씩 observe 하는 로직
    //     detailViewModel.result.observe(this) {
    //         if (it != null) {
    //             when (detailViewModel.getClubDetail.value) {
    //                 in 200..299 -> {
    //                     Log.d("TAG", "GetDetail : Status - ${detailViewModel.getClubDetail.value}")
    //                     val intent = Intent(requireActivity(), MainActivity::class.java)
    //                     Log.d("TAG", detailViewModel.result.value.toString())
    //                     intent.putExtra("isProfile", true)
    //                     intent.putExtra("result", detailViewModel.result.value)
    //                     startActivity(intent)
    //                 }
    //                 else -> {
    //                     shortToast("동아리 정보를 불러오지 못했습니다.")
    //                     Log.d(
    //                         "TAG",
    //                         "GetDetail : Error Status - ${detailViewModel.getClubDetail.value}"
    //                     )
    //                 }
    //             }
    //         }
    //     }
    // }
}