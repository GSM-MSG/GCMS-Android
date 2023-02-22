package com.msg.gcms.presentation.adapter.add_member

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.msg.gcms.databinding.ListAddMemberBinding

class AddMemberAdapter : RecyclerView.Adapter<AddMemberAdapter.AddMemberViewHolder>() {

    private val list = mutableListOf<AddMemberType>()

    class AddMemberViewHolder(
        private val binding: ListAddMemberBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AddMemberType) {
            binding.addUserNameTv.text = data.userName
        }
    }

    fun replaceItems(newList: List<AddMemberType>) {
        val diffCallback = AddMemberCallBack(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        list.clear()
        list.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMemberViewHolder {
        val binding =
            ListAddMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddMemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddMemberViewHolder, position: Int) {
        holder.bind(data = list[position])
        holder.itemView.setOnClickListener {

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

class AddMemberCallBack(
    private val oldList: List<AddMemberType>,
    private val newList: List<AddMemberType>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return if (oldItem == newItem) {
            oldItem.uuid == newItem.uuid
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}