package com.kevkhv.fuellist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kevkhv.fuellist.databinding.CardLitersBinding
import com.kevkhv.fuellist.dto.Liters


class LitersAdapter(
    private val onInteractionListener: OnInteractionListener<Liters>
) :
    androidx.recyclerview.widget.ListAdapter<Liters, LitersViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LitersViewHolder {
        val binding = CardLitersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LitersViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: LitersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallback : DiffUtil.ItemCallback<Liters>() {

        override fun areItemsTheSame(oldItem: Liters, newItem: Liters) =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Liters, newItem: Liters) =
            oldItem == newItem
    }


}


class LitersViewHolder(
    private val binding: CardLitersBinding,
    private val onInteractionListener: OnInteractionListener<Liters>
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(liters: Liters){
        binding.countLiters.text = liters.litersCount.toString()
    }
}