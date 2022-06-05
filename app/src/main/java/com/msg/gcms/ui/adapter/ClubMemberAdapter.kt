package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.msg.gcms.data.local.entity.DetailPageUserInfo
import com.msg.gcms.databinding.ListClubMemberBinding

class ClubMemberAdapter :
    ListAdapter<DetailPageUserInfo, ClubMemberAdapter.ClubMemberViewHolder>(diffUtil) {
    inner class ClubMemberViewHolder(private val binding: ListClubMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailPageUserInfo) {
            binding.userName.text = data.name
            binding.userProfileIv.load(data.imgUrl)
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
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<DetailPageUserInfo>() {
            override fun areItemsTheSame(
                oldItem: DetailPageUserInfo,
                newItem: DetailPageUserInfo
            ): Boolean {
                if (oldItem.name == newItem.name && oldItem.imgUrl == newItem.imgUrl) {
                    return true
                }
                return false
            }

            override fun areContentsTheSame(
                oldItem: DetailPageUserInfo,
                newItem: DetailPageUserInfo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}