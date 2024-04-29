package com.msg.gcms.presentation.adapter.attend

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.msg.gcms.R
import com.msg.gcms.databinding.ListMemberAttendBinding
import com.msg.gcms.domain.data.attend.GetClubAttendListResponseData

class ClubAttendListAdapter(private val itemList: List<GetClubAttendListResponseData.User>) :
    RecyclerView.Adapter<ClubAttendListAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListMemberAttendBinding, listener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetClubAttendListResponseData.User) {
            val context = binding.userName.context
            binding.member = item
            binding.userClassTxt.text = "${item.grade}학년 ${item.classNum}반 ${item.number}번"
            when (item.attendanceStatus) {
                "ATTENDANCE" -> {
                    binding.userName.setTextColor(ContextCompat.getColor(context, R.color.dark_blue3))
                    binding.attendState.setBackgroundResource(R.drawable.ic_attend_check)
                }
                "LATE" -> {
                    binding.userName.setTextColor(ContextCompat.getColor(context, R.color.yellow))
                    binding.attendState.setBackgroundResource(R.drawable.ic_attend_late)
                }
                "REASONABLE_ABSENT" -> {
                    binding.userName.setTextColor(ContextCompat.getColor(context, R.color.gray_5))
                    binding.attendState.setBackgroundResource(R.drawable.ic_attend_sick)
                }
                "ABSENT" -> {
                    binding.userName.setTextColor(ContextCompat.getColor(context, R.color.red_error))
                    binding.attendState.setBackgroundResource(R.drawable.ic_attend_absent)
                }
                else -> {
                    binding.userName.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
            }
        }

        init {
            binding.checkAttendMember.setOnClickListener {
                listener.select(adapterPosition, it.isSelected)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListMemberAttendBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )
    }

    override fun getItemCount(): Int = itemList.size

    interface OnItemClickListener {
        fun select(position: Int, isChecked: Boolean)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener
}