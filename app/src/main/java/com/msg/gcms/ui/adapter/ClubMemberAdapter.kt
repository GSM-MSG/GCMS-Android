package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.databinding.ListClubMemberBinding

class ClubMemberAdapter(private val items: List<UserData>) :
    RecyclerView.Adapter<ClubMemberAdapter.MemberViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding =
            ListClubMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    class MemberViewHolder(
        val binding: ListClubMemberBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UserData) {
            binding.userName.text = data.name
            binding.userProfileIv.load(data.userImg) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(items[position])
    }



}