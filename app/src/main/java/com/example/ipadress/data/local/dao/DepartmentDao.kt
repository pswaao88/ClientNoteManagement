package com.example.ipadress.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ipadress.data.local.entity.Department
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDao {
    @Query("SELECT * FROM departments WHERE clientId = :clientId ORDER BY name ASC")
    fun observeByClientId(clientId: Long): Flow<List<Department>>

    @Query("SELECT * FROM departments WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): Department?

    @Query(
        """
        SELECT * FROM departments
        WHERE clientId = :clientId
          AND (
              name LIKE '%' || :query || '%'
           OR managerName LIKE '%' || :query || '%'
           OR phone LIKE '%' || :query || '%'
           OR memo LIKE '%' || :query || '%'
          )
        ORDER BY name ASC
        """
    )
    fun search(clientId: Long, query: String): Flow<List<Department>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(department: Department): Long

    @Update
    suspend fun update(department: Department): Int

    @Delete
    suspend fun delete(department: Department): Int

    @Query("DELETE FROM departments WHERE id = :id")
    suspend fun deleteById(id: Long): Int
}
