package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.data.local.entity.ActivityPhotoType
import com.msg.gcms.databinding.ListDetailClubPromotionBinding

class DetailPhotoAdapter :
    ListAdapter<ActivityPhotoType, DetailPhotoAdapter.ActivityPhotoViewHolder>(diffCallBack) {

    class ActivityPhotoViewHolder(private val binding: ListDetailClubPromotionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ActivityPhotoType) {
            binding.image.load(data.activityPhoto)
            {
                transformations(RoundedCornersTransformation(9f,9f,9f,9f))
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
        val diffCallBack = object : DiffUtil.ItemCallback<ActivityPhotoType>() {
            override fun areItemsTheSame(
                oldItem: ActivityPhotoType,
                newItem: ActivityPhotoType
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ActivityPhotoType,
                newItem: ActivityPhotoType
            ): Boolean {
                return oldItem.activityPhoto == newItem.activityPhoto
            }
        }
    }
}