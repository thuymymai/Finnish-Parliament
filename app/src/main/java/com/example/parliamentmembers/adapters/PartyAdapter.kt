package com.example.parliamentmembers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.parliamentmembers.databinding.ListItemPartyBinding

class PartyAdapter(val clickListener: PartyListListener) :
    ListAdapter<String, PartyAdapter.ViewHolder>(PartyDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val party = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener.onClick(party)
        }
        holder.bind(getItem(position), clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemPartyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, clickListener: PartyListListener) {
            binding.partyName.text = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPartyBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PartyDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class PartyListListener(val clickListener: (party: String) -> Unit) {
    fun onClick(party: String) = clickListener(party)
}
