package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.msg.gcms.data.local.entity.PromotionPicType
import com.msg.gcms.databinding.ListDetailClubPromotionBinding

class ClubActivitysAdapter :
    ListAdapter<PromotionPicType, ClubActivitysAdapter.ClubPromotionViewHolder>(
        diffUtil
    ) {
    inner class ClubPromotionViewHolder(private val binding: ListDetailClubPromotionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PromotionPicType) {
            binding.promotionImg.load(data.promotionUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubPromotionViewHolder {
        return ClubPromotionViewHolder(
            ListDetailClubPromotionBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ClubPromotionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PromotionPicType>() {
            override fun areItemsTheSame(
                oldItem: PromotionPicType,
                newItem: PromotionPicType
            ): Boolean {
                return oldItem.promotionUrl == newItem.promotionUrl
            }

            override fun areContentsTheSame(
                oldItem: PromotionPicType,
                newItem: PromotionPicType
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}