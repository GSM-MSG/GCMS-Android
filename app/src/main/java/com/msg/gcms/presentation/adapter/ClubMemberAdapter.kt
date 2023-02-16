package com.msg.gcms.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.msg.gcms.databinding.ListClubMemberBinding
import com.msg.gcms.domain.data.club.get_club_detail.ClubMemberData

class ClubMemberAdapter(private val items: List<ClubMemberData>) :
    RecyclerView.Adapter<ClubMemberAdapter.MemberViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding =
            ListClubMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding, itemClickListener)
    }

    override fun getItemCount(): Int = items.size

    class MemberViewHolder(
        val binding: ListClubMemberBinding,
        listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ClubMemberData) {
            binding.userName.text = data.name
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
        holder.bind(items[position])
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener
}