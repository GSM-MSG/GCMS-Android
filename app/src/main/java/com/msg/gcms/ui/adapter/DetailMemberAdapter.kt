package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.msg.gcms.data.remote.dto.datasource.club.response.MemberSummaryResponse
import com.msg.gcms.databinding.ListClubMemberBinding

class DetailMemberAdapter :
    ListAdapter<MemberSummaryResponse, DetailMemberAdapter.ClubMemberViewHolder>(diffCallBack) {

    class ClubMemberViewHolder(private val binding: ListClubMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MemberSummaryResponse) {
            binding.nameTv.text = data.name
            binding.userProfileIv.load(data.userImg){
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
        val diffCallBack = object : DiffUtil.ItemCallback<MemberSummaryResponse>() {
            override fun areItemsTheSame(
                oldItem: MemberSummaryResponse,
                newItem: MemberSummaryResponse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MemberSummaryResponse,
                newItem: MemberSummaryResponse
            ): Boolean {
                return oldItem.name == newItem.name && oldItem.userImg == newItem.userImg
            }
        }
    }
}