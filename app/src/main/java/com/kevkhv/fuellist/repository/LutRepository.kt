package com.kevkhv.fuellist.repository

import androidx.lifecycle.LiveData
import com.kevkhv.fuellist.dto.Liters
import com.kevkhv.fuellist.dto.Lut

interface LutRepository {
    fun get(): LiveData<List<Lut>>
    fun save(lut: Lut)
    fun removeByID(lutId: Int)
    fun addLiters(liters: Liters)
}