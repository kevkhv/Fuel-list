package com.kevkhv.fuellist.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kevkhv.fuellist.databinding.CardLitersBinding
import com.kevkhv.fuellist.dto.Liters
import com.kevkhv.fuellist.R


interface LitersInteractionListener {
    fun onLitersEditClicked(liters: Liters)
    fun onLitersRemoveClicked(liters: Liters)
}


class LitersAdapter(
    private val onInteractionListener:
    LitersInteractionListener
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
    private val onInteractionListener: LitersInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(liters: Liters) {
        with(binding) {
            countLiters.text = liters.litersCount.toString()
            dateFul.text = liters.date.toString()

            itemView.setOnLongClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_lut)
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.remove -> {
                                Log.d("click", "you click del")
                                onInteractionListener.onLitersRemoveClicked(liters)
                                Log.d("click", liters.id.toString())
                                //TODO Confirmation remove
                                true
                            }
                            R.id.edit -> {
                                Log.d("click", "you click edit")
                                true
                            }
                            else -> {
                                false
                            }
                        }
                    }
                }.show()
                true
            }
        }
    }
}