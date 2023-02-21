package com.msg.gcms.presentation.adapter.user_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.msg.gcms.databinding.ListSearchMemberBinding
import com.msg.gcms.domain.data.user.search_user.GetSearchUserData

class UserSearchAdapter:
    RecyclerView.Adapter<UserSearchAdapter.SearchUserViewHolder>() {

    private var list = mutableListOf<GetSearchUserData>()

    class SearchUserViewHolder(
        private val binding: ListSearchMemberBinding,
        listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GetSearchUserData) {
            binding.userNameTv.text = data.name
            binding.userInfoTv.text = "${data.grade}학년 ${data.classNum}반 ${data.number}번"
            binding.userProfileIv.load(data.profileImg) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    fun submitList(list : List<GetSearchUserData>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    // fun addMemberList(userData: UserData) {
    //     this.memberList.add(userData)
    // }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        val binding =
            ListSearchMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = SearchUserViewHolder(binding, itemClickListener)
        return viewHolder
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
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