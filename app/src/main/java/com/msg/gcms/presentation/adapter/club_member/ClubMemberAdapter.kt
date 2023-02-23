package com.msg.gcms.presentation.adapter.club_member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.msg.gcms.databinding.ListClubMemberBinding
import com.msg.gcms.presentation.adapter.add_member.AddMemberType

class ClubMemberAdapter :
    ListAdapter<AddMemberType ,ClubMemberAdapter.MemberViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding =
            ListClubMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding, itemClickListener)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<AddMemberType>() {
            override fun areItemsTheSame(
                oldItem: AddMemberType,
                newItem: AddMemberType
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: AddMemberType,
                newItem: AddMemberType
            ): Boolean = oldItem.uuid == newItem.uuid
        }
    }

    class MemberViewHolder(
        val binding: ListClubMemberBinding,
        listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: AddMemberType) {
            binding.userName.text = data.userName
            binding.userProfileIv.load(data.userImg) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            binding.executePendingBindings()
        }

        init {
            binding.root.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener
}