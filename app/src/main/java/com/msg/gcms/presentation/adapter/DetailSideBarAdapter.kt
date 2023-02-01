package com.msg.gcms.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msg.gcms.data.local.entity.DetailPageSideBar
import com.msg.gcms.databinding.ListDetailSidebarBinding

class DetailSideBarAdapter(private val list: List<DetailPageSideBar>) :
    RecyclerView.Adapter<DetailSideBarAdapter.SideBarViewHolder>() {
    class SideBarViewHolder(
        private val binding: ListDetailSidebarBinding,
        listener: DetailSideBarAdapter.OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailPageSideBar) {
            binding.titleTv.text = item.title
            binding.iconIv.setImageResource(item.icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SideBarViewHolder {
        val binding =
            ListDetailSidebarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = SideBarViewHolder(binding, itemClickListener)
        return viewHolder
    }

    override fun onBindViewHolder(holder: SideBarViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener
}