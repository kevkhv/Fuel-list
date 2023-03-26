package com.kevkhv.fuellist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kevkhv.fuellist.R
import com.kevkhv.fuellist.databinding.CardLutBinding
import com.kevkhv.fuellist.dto.Lut

interface LutInteractionListener {
    fun onLutEditClicked(lut: Lut)
    fun onLutRemoveClicked(lut: Lut)
    fun showAddFuelDialog(lutId: Int)
    fun showBottomSheetWithLiters(lutId:Int)

}

class LutAdapter(
    private val onInteractionListener: LutInteractionListener
) :
    ListAdapter<Lut, LutViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LutViewHolder {
        val binding = CardLutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LutViewHolder(binding, onInteractionListener)
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

class LutViewHolder(
    private val binding: CardLutBinding,
    private val onInteractionListener: LutInteractionListener
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(lut: Lut) {
        binding.apply {
            titleView.text = lut.month
            textView.text = "Пробег на начало периода: ${lut.startingMileage}"
            textView2.text = "Пробег на конец периода: ${lut.endMileage}"
            textView3.text = "Заправлено литров: ${lut.litresTotal}"
            textView4.text = "расчет расхода"
            showLitersList.setOnClickListener {
                onInteractionListener.showBottomSheetWithLiters(lut.id)
            }


            addFuelButton.setOnClickListener {
                onInteractionListener.showAddFuelDialog(lut.id)
            }
            menuButton.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_lut)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onLutRemoveClicked(lut)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onLutEditClicked(lut)
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