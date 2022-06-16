package com.msg.gcms.ui.component.club

import android.content.Intent
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.msg.gcms.R
import com.msg.gcms.databinding.FragmentClubBinding
import com.msg.gcms.ui.adapter.ClubListAdapter
import com.msg.gcms.ui.base.BaseFragment
import com.msg.gcms.ui.component.club.detail.DetailFragment
import com.msg.gcms.ui.component.clubmaker.MakeClubActivity
import com.msg.gcms.ui.component.profile.ProfileActivity
import com.msg.viewmodel.ClubDetailViewModel
import com.msg.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClubFragment : BaseFragment<FragmentClubBinding>(R.layout.fragment_club) {
    private val TAG = "ClubFragment"
    private val viewModel by activityViewModels<MainViewModel>()
    val detailViewModel by activityViewModels<ClubDetailViewModel>()
    private lateinit var adapter: ClubListAdapter
    override fun init() {
        viewModel.getClubList()
        recyclerview()
        clickProfile()
        clickMakeClubBtn()
        clubTxt()
    }

    private fun recyclerview() {
        binding.clubRecyclerView.layoutManager = GridLayoutManager(context, 2)
        viewModel.clubData.observe(this) {
            adapter = ClubListAdapter(viewModel.clubData.value)
            adapter.setItemOnClickListener(object : ClubListAdapter.OnItemClickListener {
                override fun onClick(position: Int) {
                    observeStatus()
                    detailViewModel.getDetail(
                        viewModel.clubData.value?.get(position)!!.type,
                        viewModel.clubData.value?.get(position)!!.title
                    )
                }
            })
            binding.clubRecyclerView.adapter = adapter
        }
    }

    private fun clubTxt() {
        viewModel.clubName.observe(this) {
            binding.clubNameTxt.text = viewModel.clubName.value
            viewModel.getClubList()
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
        detailViewModel.getDetailStatus.observe(this) {
            when (detailViewModel.getDetailStatus.value) {
                in 200..299 -> {
                    Log.d(TAG, "GetDetail : Status - $it")
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_club, DetailFragment()).commit()
                }
                else -> {
                    Log.d(TAG, "GetDetail : Error Status - $it")
                }
            }
        }
    }
}
