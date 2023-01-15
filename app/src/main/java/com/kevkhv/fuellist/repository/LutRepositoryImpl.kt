package com.kevkhv.fuellist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kevkhv.fuellist.db.LutDao
import com.kevkhv.fuellist.dto.Lut
import com.kevkhv.fuellist.entity.LutEntity.Companion.toEntity
import com.kevkhv.fuellist.entity.LutEntity.Companion.toModel

class LutRepositoryImpl(private val dao: LutDao) : LutRepository {

    override fun get(): LiveData<List<Lut>> = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun save(lut: Lut) = dao.save(lut.toEntity())

    override fun removeByID(lutId: Int) = dao.removeById(lutId)
}