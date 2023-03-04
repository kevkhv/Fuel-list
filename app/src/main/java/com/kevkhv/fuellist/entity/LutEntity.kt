package com.kevkhv.fuellist.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kevkhv.fuellist.dto.Lut

@Entity(tableName = "lutsTable")
class LutEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    val month: String,
    val litresTotal: Int,
    val residueLitres:Int,
    val startingMileage: Int,
    val endMileage: Int
) {


    companion object {
        internal fun LutEntity.toModel() = Lut(
            id = id,
            month = month,
            litresTotal = litresTotal,
            residueLitres = residueLitres,
            startingMileage = startingMileage,
            endMileage = endMileage
        )

        internal fun Lut.toEntity() = LutEntity(
            id = id,
            month = month,
            litresTotal = litresTotal,
            residueLitres = residueLitres,
            startingMileage = startingMileage,
            endMileage = endMileage
        )
    }
}