package com.msg.gcms.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msg.gcms.data.remote.dto.datasource.user.response.AftersData
import com.msg.gcms.databinding.ListAfterSchoolBinding

class AfterSchoolListAdapter(private val itemList: List<AftersData>): RecyclerView.Adapter<AfterSchoolListAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListAfterSchoolBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(after: AftersData) {
            binding.afterSchoolItem = after
            binding.gradeTxt.text = "${after.grade}학년"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("안ㄴ", "onCreateViewHolder: ${itemList}")
        val binding = ListAfterSchoolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = AfterSchoolListAdapter.ViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList.get(position))
    }

    override fun getItemCount(): Int = itemList.size
}