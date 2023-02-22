package com.msg.gcms.presentation.adapter.add_member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.msg.gcms.databinding.ListAddMemberBinding

class AddMemberAdapter :
    ListAdapter<AddMemberType, AddMemberAdapter.AddMemberViewHolder>(AddMemberCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMemberViewHolder {
        val binding =
            ListAddMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddMemberViewHolder(binding)
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    override fun onBindViewHolder(holder: AddMemberViewHolder, position: Int) {
        holder.bind(data = getItem(position))
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(position)
        }
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    inner class AddMemberViewHolder(
        private val binding: ListAddMemberBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AddMemberType) {
            binding.addUserNameTv.text = data.userName
        }
    }

    private class AddMemberCallback : DiffUtil.ItemCallback<AddMemberType>() {
        override fun areItemsTheSame(oldItem: AddMemberType, newItem: AddMemberType): Boolean =
            oldItem.uuid == newItem.uuid

        override fun areContentsTheSame(
            oldItem: AddMemberType,
            newItem: AddMemberType
        ): Boolean = oldItem == newItem
    }
}
