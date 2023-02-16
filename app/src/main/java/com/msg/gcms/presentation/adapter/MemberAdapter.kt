package com.msg.gcms.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.msg.gcms.databinding.ListMemberBinding
import com.msg.gcms.domain.data.club_member.get_club_member.MemberData

class MemberAdapter(private val itemList: List<MemberData>, private val role: String) :
    RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListMemberBinding, listener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MemberData, role: String) {
            binding.member = item
            binding.userProfileImg.load(item.userImg){
                transformations(CircleCropTransformation())
            }
            binding.userClassTxt.text = "${item.grade}학년 ${item.`class`}반 ${item.num}번"

            if(role == "MEMBER" || item.equals("HEAD")) {
                binding.withdrawalBtn.visibility = View.INVISIBLE
                binding.mandateBtn.visibility = View.INVISIBLE
            }
        }

        init {
            binding.mandateBtn.setOnClickListener {
                listener.mandate(adapterPosition)
            }
            binding.withdrawalBtn.setOnClickListener {
                listener.kick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListMemberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position], role)
    }

    override fun getItemCount() = itemList.size

    interface OnItemClickListener{
        fun mandate(position: Int)
        fun kick(position: Int)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener
}