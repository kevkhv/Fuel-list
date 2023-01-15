package com.kevkhv.fuellist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kevkhv.fuellist.databinding.CardLutBinding
import com.kevkhv.fuellist.dto.Lut

interface OnInteractionListener {
    fun onEdit(lut: Lut){}
    fun onRemoveById(lut: Lut)
}

class LutAdapter(private val onInteractionListener:OnInteractionListener) :
    ListAdapter<Lut, LutViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LutViewHolder {
        val binding = CardLutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LutViewHolder(binding,onInteractionListener)
    }

    override fun onBindViewHolder(holder: LutViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallback : DiffUtil.ItemCallback<Lut>() {

        override fun areItemsTheSame(oldItem: Lut, newItem: Lut) =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Lut, newItem: Lut) =
            oldItem == newItem
    }
}