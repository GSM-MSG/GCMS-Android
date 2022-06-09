package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.data.local.entity.PromotionPicType
import com.msg.gcms.databinding.ListDetailClubPromotionBinding
import com.msg.gcms.ui.base.BaseDiffUtil

class ClubActivitysAdapter :
    ListAdapter<PromotionPicType, ClubActivitysAdapter.ClubPromotionViewHolder>(
        BaseDiffUtil<PromotionPicType>()
    ) {
    inner class ClubPromotionViewHolder(private val binding: ListDetailClubPromotionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PromotionPicType) {
            binding.promotionImg.load(data.promotionUrl){
                crossfade(true)
                transformations(RoundedCornersTransformation(6f))
            }

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
}