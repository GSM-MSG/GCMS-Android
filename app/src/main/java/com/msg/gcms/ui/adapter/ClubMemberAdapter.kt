package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.databinding.ListClubMemberBinding

class ClubMemberAdapter : ListAdapter<UserData, ClubMemberAdapter.ClubMemberViewHolder>(diffUtil) {
    inner class ClubMemberViewHolder(private val binding: ListClubMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserData) {
            binding.userName.text = data.name
            binding.userProfileIv.load(data.userImg)
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
        val diffUtil = object : DiffUtil.ItemCallback<UserData>() {
            override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                if (oldItem.name == newItem.name && oldItem.userImg == newItem.userImg) {
                    return true
                }
                return false
            }

            override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                return oldItem == newItem
            }
        }
    }
}