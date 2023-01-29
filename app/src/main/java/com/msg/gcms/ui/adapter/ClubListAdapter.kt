package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.data.remote.dto.club.response.SummaryClubResponse
import com.msg.gcms.databinding.ListClubSummaryBinding

class ClubListAdapter(private val itemList: List<SummaryClubResponse>?) :
    RecyclerView.Adapter<ClubListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListClubSummaryBinding, listener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SummaryClubResponse?) {
            binding.clubSummary = data
            binding.itemClubImg.load(data?.bannerUrl) {
                transformations(RoundedCornersTransformation(9f, 9f, 0f, 0f))
            }
        }

        init {
            binding.clubSummaryLayout.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListClubSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding, itemClickListener)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList?.get(position))
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener
}