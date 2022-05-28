package com.msg.gcms.ui.component.clubmaker.clubDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.msg.gcms.data.local.entity.ActivityPhotoType
import com.msg.gcms.databinding.ListClubPictureBinding

class ActivityPhotosAdapter(private val items: MutableList<ActivityPhotoType>) : RecyclerView.Adapter<ActivityPhotosAdapter.BannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ListClubPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class BannerViewHolder(
        val binding: ListClubPictureBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ActivityPhotoType) {
            binding.activityPhoto.load(data.activityPhoto)
        }
    }
}