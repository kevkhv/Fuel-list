package com.kevkhv.fuellist.dto

import android.text.Editable

data class Lut(
    val id: Int,
    val month: String,
    val litresTotal: Int,
    val residueLitres: Int,
    val endMonthLiters: Int,
    val startingMileage: Int,
    val endMileage: Int
)