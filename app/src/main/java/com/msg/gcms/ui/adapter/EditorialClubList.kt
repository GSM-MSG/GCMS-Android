package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.data.remote.dto.datasource.user.response.ClubData
import com.msg.gcms.databinding.ListClubEditorialBinding

class EditorialClubList(private val clubList: ArrayList<ClubData>): RecyclerView.Adapter<EditorialClubList.ViewHolder>() {
    class ViewHolder(val binding: ListClubEditorialBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(club: ClubData) {
            binding.editorialItem = club
            binding.itemClubImg.load(club.bannerUrl) {
                transformations(RoundedCornersTransformation(9f, 9f, 0f, 0f))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListClubEditorialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = EditorialClubList.ViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clubList?.get(position))
    }

    override fun getItemCount(): Int {
        return clubList.size
    }
}