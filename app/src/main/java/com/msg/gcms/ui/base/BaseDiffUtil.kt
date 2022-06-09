package com.msg.gcms.ui.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.msg.gcms.data.local.entity.PromotionPicType

class BaseDiffUtil<T: Any> : DiffUtil.ItemCallback<T>(){

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}