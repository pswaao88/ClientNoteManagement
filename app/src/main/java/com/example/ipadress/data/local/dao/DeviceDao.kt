package com.example.ipadress.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ipadress.data.local.entity.Device
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {
    @Query("SELECT * FROM devices WHERE departmentId = :departmentId ORDER BY modelName ASC")
    fun observeByDepartmentId(departmentId: Long): Flow<List<Device>>

    @Query("SELECT * FROM devices WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): Device?

    @Query(
        """
        SELECT * FROM devices
        WHERE departmentId = :departmentId
          AND (
              modelName LIKE '%' || :query || '%'
           OR ipAddress LIKE '%' || :query || '%'
           OR loginId LIKE '%' || :query || '%'
           OR IFNULL(memo, '') LIKE '%' || :query || '%'
          )
        ORDER BY modelName ASC
        """
    )
    fun search(departmentId: Long, query: String): Flow<List<Device>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(device: Device): Long

    @Update
    suspend fun update(device: Device): Int

    @Delete
    suspend fun delete(device: Device): Int

    @Query("DELETE FROM devices WHERE id = :id")
    suspend fun deleteById(id: Long): Int
}
