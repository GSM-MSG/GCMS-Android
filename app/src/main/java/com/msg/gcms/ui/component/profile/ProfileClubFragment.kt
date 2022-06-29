package com.msg.gcms.ui.component.profile

import android.graphics.Rect
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.R
import com.msg.gcms.data.remote.dto.datasource.user.response.ClubData
import com.msg.gcms.databinding.FragmentProfileClubBinding
import com.msg.gcms.ui.adapter.EditorialClubAdapter
import com.msg.gcms.ui.base.BaseFragment
import com.msg.viewmodel.ProfileViewModel

class ProfileClubFragment: BaseFragment<FragmentProfileClubBinding>(R.layout.fragment_profile_club) {
    private val viewModel by activityViewModels<ProfileViewModel>()
    private val privateClubList: ArrayList<ClubData> = ArrayList()
    private lateinit var adapter: EditorialClubAdapter
    override fun init() {
        isAfterSchool()
        viewClub()
    }

    private fun isAfterSchool() {
        viewModel.afterSchoolStatus.observe(this) {
            if (it) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.afterSchoolFragmentView, ProfileAfterSchoolFragment()).commit()
            }
        }
    }

    private fun viewClub() {
        viewModel.profileData.observe(this) {
            it.clubs.forEach {
                when (it.type) {
                    "MAJOR" -> {
                        binding.apply {
                            noMajorClubImg.visibility = View.GONE
                            majorClubLayout.visibility = View.VISIBLE
                            majorClubImg.load(it.bannerUrl) {
                                transformations(RoundedCornersTransformation(9f, 9f, 0f, 0f))
                            }
                            majorClubName.text = it.title
                        }
                    }
                    "FREEDOM" -> {
                        binding.apply {
                            noFreedomClubImg.visibility = View.GONE
                            freedomClubLayout.visibility = View.VISIBLE
                            freedomClubImg.load(it.bannerUrl) {
                                transformations(RoundedCornersTransformation(9f, 9f, 0f, 0f))
                            }
                            freedomClubName.text = it.title
                        }
                    }
                    "EDITORIAL" -> {
                        binding.apply {
                            privateClub.visibility = View.VISIBLE
                            privateClubRecyclerview.visibility = View.VISIBLE
                            privateClubList.add(it)
                        }
                    }
                }
            }
            setRecyclerView()
        }
    }

    private fun setRecyclerView() {
        binding.privateClubRecyclerview.apply {
            adapter = EditorialClubAdapter(privateClubList)
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(HorizontalItemDecorator(20))
        }
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
            outRect.bottom = divHeight*2
        }
    }
}