package com.kevkhv.fuellist.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kevkhv.fuellist.dto.Lut
import com.kevkhv.fuellist.entity.LutEntity

@Dao
interface LutDao {
    @Query("SELECT * FROM lutsTable ORDER BY id DESC")
    fun getAll(): LiveData<List<LutEntity>>

    @Insert
    fun insert(lut: LutEntity)

    fun save(lut: LutEntity) =
        if (lut.id == 0)
            insert(lut)
        else updateContentById(lut.id,
            lut.month,
            lut.litresTotal,
            lut.startingMileage,
            lut.endMileage
        )

    @Query(
        """UPDATE lutsTable SET
         month = :month, 
         litresTotal = :litresTotal,
         startingMileage = :startingMileage,
         endMileage = :endMileage
         WHERE id = :id
         """
    )
    fun updateContentById(
        id: Int, month: String, litresTotal: Int, startingMileage: Int, endMileage: Int
    )

    @Query("DELETE FROM lutsTable WHERE id = :id")
    fun removeById(id: Int)
}