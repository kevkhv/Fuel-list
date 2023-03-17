package com.kevkhv.fuellist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kevkhv.fuellist.dto.Liters
import com.kevkhv.fuellist.dto.Lut
import com.kevkhv.fuellist.repository.LutRepository
import com.kevkhv.fuellist.repository.LutRepositoryImpl
import ru.netology.nmedia.db.AppDb

private val empty = Lut(
    id = 0,
    month = "",
    litresTotal = 0,
    residueLitres = 0,
    startingMileage = 0,
    endMileage = 0
)

class ViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository: LutRepository =
//        LutRepositoryImpl(dao = AppDb.getInstance(context = application).lutDao)


    private val repository: LutRepository = LutRepositoryImpl(application)

    val data = repository.get()

    val edited = MutableLiveData(empty)

    fun save(lut: Lut) = repository.save(lut)

    fun removeByID(lutId: Int) = repository.removeByID(lutId)

    fun edit(lut: Lut) {
        edited.value = lut
    }

    fun addLiters(liters: Liters) = repository.addLiters(liters)


}