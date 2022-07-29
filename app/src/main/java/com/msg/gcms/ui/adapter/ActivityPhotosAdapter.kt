package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msg.gcms.data.local.entity.ActivityPhotoType
import com.msg.gcms.databinding.ListClubPictureBinding

class ActivityPhotosAdapter(private val items: List<ActivityPhotoType>) :
    RecyclerView.Adapter<ActivityPhotosAdapter.BannerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding =
            ListClubPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(position)
        }
    }

    class BannerViewHolder(val binding: ListClubPictureBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ActivityPhotoType) {
            binding.activityPhoto.setImageBitmap(data.activityPhoto)
            binding.executePendingBindings()
        }

        init {
            binding.activityPhoto.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }
    }


    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener
}