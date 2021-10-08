package com.example.parliamentmembers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.parliamentmembers.databinding.ListItemMembersBinding
import com.example.parliamentmembers.roomdb.ParliamentMember

class MemberAdapter(val clickListener: MemberListListener) :
    ListAdapter<ParliamentMember, MemberAdapter.ViewHolder>(MemberDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemMembersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParliamentMember, clickListener: MemberListListener) {
            binding.member = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemMembersBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class MemberDiffCallback : DiffUtil.ItemCallback<ParliamentMember>() {
    override fun areItemsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem.personNumber == newItem.personNumber
    }

    override fun areContentsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem == newItem
    }
}

class MemberListListener(val clickListener: (personalNumber: Int) -> Unit) {
    fun onClick(mem: ParliamentMember) = clickListener(mem.personNumber)
}
