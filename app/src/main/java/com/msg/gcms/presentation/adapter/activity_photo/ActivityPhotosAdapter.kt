package com.msg.gcms.presentation.adapter.activity_photo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.msg.gcms.databinding.ListClubPictureBinding

class ActivityPhotosAdapter :
    ListAdapter<ActivityPhotoType, ActivityPhotosAdapter.ActivityPhotoHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityPhotoHolder {
        val binding =
            ListClubPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActivityPhotoHolder(binding, itemClickListener)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ActivityPhotoType>() {
            override fun areItemsTheSame(
                oldItem: ActivityPhotoType,
                newItem: ActivityPhotoType
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ActivityPhotoType,
                newItem: ActivityPhotoType
            ): Boolean = oldItem.activityPhoto.hashCode() == newItem.activityPhoto.hashCode()
        }
    }

    override fun onBindViewHolder(holder: ActivityPhotoHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(position)
        }
    }

    class ActivityPhotoHolder(val binding: ListClubPictureBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ActivityPhotoType) {
            binding.activityPhoto.setImageBitmap(data.activityPhoto)
            binding.activityPhoto.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.executePendingBindings()
        }

        init {
            binding.activityPhoto.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }
    }


    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener
}