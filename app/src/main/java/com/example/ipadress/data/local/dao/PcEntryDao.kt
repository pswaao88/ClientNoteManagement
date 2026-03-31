package com.example.ipadress.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ipadress.data.local.entity.PcEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface PcEntryDao {
    @Query("SELECT * FROM pc_entries WHERE departmentId = :departmentId ORDER BY IFNULL(seatSuffix, '') ASC")
    fun observeByDepartmentId(departmentId: Long): Flow<List<PcEntry>>

    @Query("SELECT * FROM pc_entries WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): PcEntry?

    @Query(
        """
        SELECT * FROM pc_entries
        WHERE departmentId = :departmentId
          AND (
              IFNULL(seatSuffix, '') LIKE '%' || :query || '%'
           OR IFNULL(ipAddress, '') LIKE '%' || :query || '%'
           OR IFNULL(loginId, '') LIKE '%' || :query || '%'
           OR IFNULL(memo, '') LIKE '%' || :query || '%'
          )
        ORDER BY IFNULL(seatSuffix, '') ASC
        """
    )
    fun search(departmentId: Long, query: String): Flow<List<PcEntry>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(pcEntry: PcEntry): Long

    @Update
    suspend fun update(pcEntry: PcEntry): Int

    @Delete
    suspend fun delete(pcEntry: PcEntry): Int

    @Query("DELETE FROM pc_entries WHERE id = :id")
    suspend fun deleteById(id: Long): Int
}
