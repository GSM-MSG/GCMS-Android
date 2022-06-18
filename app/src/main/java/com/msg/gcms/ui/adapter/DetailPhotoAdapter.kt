package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.msg.gcms.data.local.entity.ActivityPhotoType
import com.msg.gcms.databinding.ListClubPictureBinding

class DetailPhotoAdapter :
    ListAdapter<ActivityPhotoType, DetailPhotoAdapter.ActivityPhotoViewHolder>(diffCallBack) {
    class ActivityPhotoViewHolder(private val binding: ListClubPictureBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ActivityPhotoType) {
            binding.activityPhoto.load(data)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityPhotoViewHolder {
        return DetailPhotoAdapter.ActivityPhotoViewHolder(
            ListClubPictureBinding.inflate(
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