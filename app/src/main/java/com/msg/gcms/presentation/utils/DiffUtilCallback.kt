package com.msg.gcms.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.msg.gcms.data.remote.dto.user.response.UserData

class DiffUtilCallback: DiffUtil.ItemCallback<UserData>() {
    override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean = oldItem.email == newItem.email
    override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean = oldItem == newItem
}