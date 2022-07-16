package com.msg.gcms.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.msg.gcms.data.remote.dto.datasource.user.response.ClubData
import com.msg.gcms.databinding.ListClubEditorialBinding

class EditorialClubAdapter(private val clubList: ArrayList<ClubData>) :
    RecyclerView.Adapter<EditorialClubAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListClubEditorialBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(club: ClubData) {
            binding.editorialItem = club
            binding.itemClubImg.load(club.bannerUrl) {
                transformations(RoundedCornersTransformation(9f, 9f, 0f, 0f))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListClubEditorialBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clubList?.get(position))
    }

    override fun getItemCount(): Int = clubList.size
}