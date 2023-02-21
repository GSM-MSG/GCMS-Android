package com.msg.gcms.presentation.adapter.add_member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msg.gcms.databinding.ListAddMemberBinding

class AddMemberAdapter : RecyclerView.Adapter<AddMemberAdapter.AddMemberViewHolder>() {

    private val list = mutableListOf<AddMemberType>()

    class AddMemberViewHolder(
        private val binding: ListAddMemberBinding,
        listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AddMemberType) {
            binding.addUserNameTv.text = data.userName
        }
    }

    fun setMemberList(list: List<AddMemberType>) {
        list.filter { it.uuid != null }.forEach {
            this.list.add(
                AddMemberType(
                    uuid = it.uuid,
                    userName = it.userName,
                    userImg = it.userImg
                )
            )
        }
        notifyDataSetChanged()
    }

    fun submitList(addUserData: AddMemberType) {
        list.add(addUserData)
        notifyDataSetChanged()
    }

    fun removeMember(removeMember: AddMemberType) {
        list.remove(removeMember)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMemberViewHolder {
        val binding =
            ListAddMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddMemberViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: AddMemberViewHolder, position: Int) {
        holder.bind(data = list[position])
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