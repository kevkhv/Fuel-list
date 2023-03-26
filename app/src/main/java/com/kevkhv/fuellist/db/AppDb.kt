package ru.netology.nmedia.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kevkhv.fuellist.db.LitersDao
import com.kevkhv.fuellist.db.LutDao
import com.kevkhv.fuellist.repository.entity.LitersEntity
import com.kevkhv.fuellist.repository.entity.LutEntity


@Database(
    entities = [
        LutEntity::class,
        LitersEntity::class
    ], version = 1
)
abstract class AppDb : RoomDatabase() {
    abstract val lutDao: LutDao
    abstract val litersDao: LitersDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDb::class.java, "app.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}