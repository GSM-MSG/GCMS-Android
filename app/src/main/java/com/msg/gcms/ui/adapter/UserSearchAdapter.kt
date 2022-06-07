package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.msg.gcms.data.remote.dto.datasource.user.response.UserData
import com.msg.gcms.databinding.ListSearchMemberBinding
import com.msg.gcms.utils.DiffUtilCallback

class UserSearchAdapter:
    ListAdapter<UserData, UserSearchAdapter.SearchUserViewHolder>(DiffUtilCallback()) {
    class SearchUserViewHolder(
        private val binding: ListSearchMemberBinding,
        listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserData) {
            binding.userNameTv.text = data.name
            binding.userInfoTv.text = "${data.grade}학년 ${data.`class`}반 ${data.num}번"
            binding.userProfileIv.load(data.userImg) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }

        init {
            binding.root.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        val binding =
            ListSearchMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = UserSearchAdapter.SearchUserViewHolder(binding, itemClickListener)
        return viewHolder
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        holder.bind(currentList[position])
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