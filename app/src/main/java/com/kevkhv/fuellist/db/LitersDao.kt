package com.kevkhv.fuellist.db

import androidx.room.Dao
import androidx.room.Insert
import com.kevkhv.fuellist.dto.Liters
import com.kevkhv.fuellist.repository.entity.LitresEntity

@Dao
interface LitersDao {
    @Insert
    fun insertLiters(liters: LitresEntity)
}
