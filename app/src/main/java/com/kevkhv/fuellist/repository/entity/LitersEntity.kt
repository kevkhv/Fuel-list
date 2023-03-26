package com.kevkhv.fuellist.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.kevkhv.fuellist.dto.Liters

@Entity(
    tableName = "liters_table",
    foreignKeys = [
        ForeignKey(
            entity = LutEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("lut_id"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )]
)
data class LitersEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "lut_id")
    val lutId: Int,
    @ColumnInfo(name = "liters_count")
    val litersCount: Int,
    @ColumnInfo(name = "date")
    val date: String
)

fun LitersEntity.toLiters() = Liters(
    id = id,
    lutId = lutId,
    litersCount = litersCount,
    date = date
)

fun Liters.toLitersEntity() = LitersEntity(
    id = id,
    lutId = lutId,
    litersCount = litersCount,
    date = date
)