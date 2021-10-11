package com.example.parliamentmembers.adapters

import android.app.Activity
import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.parliamentmembers.databinding.ListItemMembersBinding
import com.example.parliamentmembers.fragments.MembersFragment
import com.example.parliamentmembers.roomdb.ParliamentMember
import com.example.parliamentmembers.util.MyApp
import java.util.*
import kotlin.collections.ArrayList

class MemberAdapter(val clickListener: MemberListListener) :
    ListAdapter<ParliamentMember, MemberAdapter.ViewHolder>(MemberDiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
        Glide.with(holder.itemView.context)
            .load("https://avoindata.eduskunta.fi/" + getItem(position).picture)
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.memberListImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemMembersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParliamentMember, clickListener: MemberListListener) {
            binding.member = item
            binding.clickListener = clickListener
            binding.constituency.text = item.constituency
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
