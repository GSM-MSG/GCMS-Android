package com.msg.gcms.ui.component.clubmaker.clubDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.msg.gcms.R
import com.msg.gcms.data.local.entity.BannerImage
import com.msg.gcms.databinding.ListClubPictureBinding

class BannerAdapter() : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    var list = mutableListOf<BannerImage>()

    inner class BannerViewHolder(
        private val binding: ListClubPictureBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BannerImage) {
            binding.itemList = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = DataBindingUtil.inflate<ListClubPictureBinding>(LayoutInflater.from(parent.context), R.layout.list_club_picture, parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}