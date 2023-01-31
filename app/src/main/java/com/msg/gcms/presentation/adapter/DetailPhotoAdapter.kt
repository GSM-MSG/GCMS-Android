package com.msg.gcms.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.data.local.entity.PromotionPicType
import com.msg.gcms.databinding.ListDetailClubPromotionBinding

class DetailPhotoAdapter :
    ListAdapter<PromotionPicType, DetailPhotoAdapter.ActivityPhotoViewHolder>(diffCallBack) {

    class ActivityPhotoViewHolder(private val binding: ListDetailClubPromotionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PromotionPicType) {
            binding.image.load(data.promotionUrl)
            {
                transformations(RoundedCornersTransformation(9f, 9f, 9f, 9f))
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityPhotoViewHolder {
        return ActivityPhotoViewHolder(
            ListDetailClubPromotionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActivityPhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<PromotionPicType>() {
            override fun areItemsTheSame(
                oldItem: PromotionPicType,
                newItem: PromotionPicType
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PromotionPicType,
                newItem: PromotionPicType
            ): Boolean {
                return oldItem.promotionUrl == newItem.promotionUrl
            }
        }
    }
}