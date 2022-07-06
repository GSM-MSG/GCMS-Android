package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.msg.gcms.data.local.entity.NavigationModel
import com.msg.gcms.databinding.ListRowNavOfSideBinding

class NavigationAdapter(private val items: ArrayList<NavigationModel>) :
    RecyclerView.Adapter<NavigationAdapter.NavigationViewHolder>() {

    class NavigationViewHolder(
        private val binding: ListRowNavOfSideBinding,
        listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NavigationModel) {
            binding.iconIv.load(data.icon)
            binding.titleTv.text = data.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationViewHolder {
        val binding = ListRowNavOfSideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NavigationViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: NavigationViewHolder, position: Int) {
        holder.bind(data = items[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int = items.count()

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    private lateinit var itemClickListener: OnItemClickListener
}