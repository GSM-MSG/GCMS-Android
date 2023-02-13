package com.msg.gcms.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.data.remote.dto.user.response.SummarizedClubInfo
import com.msg.gcms.databinding.ListClubEditorialBinding

class EditorialClubAdapter(private val clubList: ArrayList<SummarizedClubInfo>) :
    RecyclerView.Adapter<EditorialClubAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListClubEditorialBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(club: SummarizedClubInfo) {
            binding.editorialItem = club
            binding.itemClubImg.load(club.bannerImg) {
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
            ListClubEditorialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding, itemClickListener)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clubList?.get(position))
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int = clubList.size

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener
}