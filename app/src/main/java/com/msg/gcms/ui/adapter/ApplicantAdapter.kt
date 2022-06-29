package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.msg.gcms.data.remote.dto.datasource.club.response.MemberSummaryResponse
import com.msg.gcms.databinding.ListApplicantBinding

class ApplicantAdapter(val itemList: List<MemberSummaryResponse>) :
    RecyclerView.Adapter<ApplicantAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListApplicantBinding, itemClickListener: onClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MemberSummaryResponse) {
            binding.applicant = item
            binding.userProfileImg.load(item.userImg){
                transformations(CircleCropTransformation())
            }
            binding.userClassTxt.text = "${item.grade}학년 ${item.`class`}반 ${item.num}번"
        }

        init {
            binding.approveBtn.setOnClickListener {
                itemClickListener.accept(adapterPosition)
            }
            binding.refuseBtn.setOnClickListener {
                itemClickListener.reject(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListApplicantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), itemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList.get(position))
    }

    override fun getItemCount() = itemList.size

    interface onClickListener {
        fun accept(position: Int)
        fun reject(position: Int)
    }

    fun setOnClickListener(clickListener: onClickListener) {
        this.itemClickListener = clickListener
    }

    lateinit var itemClickListener: onClickListener
}