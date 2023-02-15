package com.msg.gcms.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.msg.gcms.R
import com.msg.gcms.databinding.ListClubMemberBinding
import com.msg.gcms.domain.data.club.MemberListData

class DetailMemberAdapter :
    ListAdapter<MemberListData, DetailMemberAdapter.ClubMemberViewHolder>(diffCallBack) {

    class ClubMemberViewHolder(private val binding: ListClubMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MemberListData) {
            binding.userName.text = data.name
            binding.userProfileIv.load(data.userImg ?: R.drawable.ic_default_profile){
                transformations(CircleCropTransformation())
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubMemberViewHolder {
        return ClubMemberViewHolder(
            ListClubMemberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ClubMemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<MemberListData>() {
            override fun areItemsTheSame(
                oldItem: MemberListData,
                newItem: MemberListData
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MemberListData,
                newItem: MemberListData
            ): Boolean {
                return oldItem.name == newItem.name && oldItem.userImg == newItem.userImg
            }
        }
    }
}