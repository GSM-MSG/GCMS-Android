package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.R
import com.msg.gcms.data.remote.dto.datasource.club.response.SummaryClubResponse
import com.msg.gcms.databinding.ListClubSummaryBinding

class ClubListAdapter(private val itemList: List<SummaryClubResponse>?) :
    RecyclerView.Adapter<ClubListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListClubSummaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SummaryClubResponse?) {
            binding.clubSummary = data
            binding.itemClubImg.load(data?.bannerUrl) {
                transformations(RoundedCornersTransformation(9f,9f,0f,0f))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListClubSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList?.get(position))
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }
}