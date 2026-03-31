package com.example.ipadress.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ipadress.data.local.dao.ClientDao
import com.example.ipadress.data.local.dao.DepartmentDao
import com.example.ipadress.data.local.dao.DeviceDao
import com.example.ipadress.data.local.dao.PcEntryDao
import com.example.ipadress.data.local.entity.Client
import com.example.ipadress.data.local.entity.Department
import com.example.ipadress.data.local.entity.Device
import com.example.ipadress.data.local.entity.PcEntry

@Database(
    entities = [
        Client::class,
        Department::class,
        Device::class,
        PcEntry::class,
    ],
    version = 4,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
    abstract fun departmentDao(): DepartmentDao
    abstract fun deviceDao(): DeviceDao
    abstract fun pcEntryDao(): PcEntryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME,
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }

        private const val DATABASE_NAME = "ipadress.db"
    }
}
