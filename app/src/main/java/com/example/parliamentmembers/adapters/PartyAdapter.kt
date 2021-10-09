package com.example.parliamentmembers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.parliamentmembers.R
import com.example.parliamentmembers.databinding.ListItemPartyBinding
import kotlinx.android.synthetic.main.list_item_party.*

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
            //binding.partyName.text = item
            binding.clickListener = clickListener
            binding.apply {
                when (item) {
                    "sd" -> {partyImage.setImageResource(R.drawable.sd)
                    partyName.setText(R.string.sd)}
                    "ps" -> {partyImage.setImageResource(R.drawable.ps)
                    partyName.setText(R.string.ps)}
                    "kd" -> {partyImage.setImageResource(R.drawable.kd)
                    partyName.setText(R.string.kd)}
                    "kesk" -> {partyImage.setImageResource(R.drawable.kesk)
                    partyName.setText(R.string.kesk)}
                    "kok" -> {partyImage.setImageResource(R.drawable.kok)
                    partyName.setText(R.string.kok)}
                    "r" -> {partyImage.setImageResource(R.drawable.r)
                    partyName.setText(R.string.r)}
                    "vas" -> {partyImage.setImageResource(R.drawable.vas)
                    partyName.setText(R.string.vas)}
                    "vihr" -> {partyImage.setImageResource(R.drawable.vihr)
                    partyName.setText(R.string.vihr)}
                    "liik" -> {partyImage.setImageResource(R.drawable.liik)
                    partyName.setText(R.string.liik)}

                }
            }
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
