package com.example.ipadress.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ipadress.data.local.entity.Client
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Query("SELECT * FROM clients ORDER BY name ASC")
    fun observeAll(): Flow<List<Client>>

    @Query("SELECT * FROM clients WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): Client?

    @Query(
        """
        SELECT * FROM clients
        WHERE name LIKE '%' || :query || '%'
           OR address LIKE '%' || :query || '%'
           OR managerName LIKE '%' || :query || '%'
           OR phone LIKE '%' || :query || '%'
           OR memo LIKE '%' || :query || '%'
        ORDER BY name ASC
        """
    )
    fun search(query: String): Flow<List<Client>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(client: Client): Long

    @Update
    suspend fun update(client: Client): Int

    @Delete
    suspend fun delete(client: Client): Int

    @Query("DELETE FROM clients WHERE id = :id")
    suspend fun deleteById(id: Long): Int
}
