package com.kevkhv.fuellist.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kevkhv.fuellist.dto.Liters
import com.kevkhv.fuellist.dto.Lut
import com.kevkhv.fuellist.repository.entity.LutEntity.Companion.toEntity
import com.kevkhv.fuellist.repository.entity.LutEntity.Companion.toModel
import com.kevkhv.fuellist.repository.entity.toLitresEntity
import ru.netology.nmedia.db.AppDb

class LutRepositoryImpl(
    private val application: Application
) : LutRepository {


    private val dao = AppDb.getInstance(application).lutDao
    private val litersDao = AppDb.getInstance(application).litersDao


    override fun get(): LiveData<List<Lut>> = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun save(lut: Lut) = dao.save(lut.toEntity())

    override fun removeByID(lutId: Int) = dao.removeById(lutId)
    override fun addLiters(liters: Liters) {
        litersDao.insertLiters(liters.toLitresEntity())
        dao.updateAllLiters(liters.lutId)
    }


}