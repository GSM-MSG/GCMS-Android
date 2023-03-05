package com.msg.gcms.presentation.adapter.editorial_club

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.databinding.ListClubEditorialBinding

class EditorialClubAdapter :
    ListAdapter<ClubType, EditorialClubAdapter.ViewHolder>(diffCallBack) {

    class ViewHolder(val binding: ListClubEditorialBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(club: ClubType) {
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

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<ClubType>() {
            override fun areItemsTheSame(
                oldItem: ClubType, newItem: ClubType
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ClubType, newItem: ClubType
            ): Boolean {
                return oldItem == newItem
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
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(position)
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