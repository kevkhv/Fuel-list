package com.kevkhv.fuellist.adapter

import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.kevkhv.fuellist.R
import com.kevkhv.fuellist.databinding.CardLutBinding
import com.kevkhv.fuellist.dto.Lut

class LutViewHolder(
    private val binding: CardLutBinding,
    private val onInteractionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(lut: Lut) {
        binding.apply {
            titleView.text = lut.month
            textView.text = "Пробег на начало периода: ${lut.startingMileage}"
            textView2.text = "Пробег на конец периода: ${lut.endMileage}"
            textView3.text = "Заправлено литров: ${lut.litresTotal}"
            textView4.text = "расчет расхода"
            addFuelButton.setOnClickListener {
                onInteractionListener.showAddFuelDialog(lut.id)
            }
            menuButton.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_lut)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemoveById(lut)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(lut)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}

