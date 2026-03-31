package com.example.ipadress.data.repository

import com.example.ipadress.data.local.dao.ClientDao
import com.example.ipadress.data.local.entity.Client
import kotlinx.coroutines.flow.Flow

class ClientRepository(
    private val clientDao: ClientDao,
) {
    fun observeAll(): Flow<List<Client>> = clientDao.observeAll()

    suspend fun getById(id: Long): Client? = clientDao.getById(id)

    fun search(query: String): Flow<List<Client>> {
        return if (query.isBlank()) {
            clientDao.observeAll()
        } else {
            clientDao.search(query.trim())
        }
    }

    suspend fun insert(client: Client): Long = clientDao.insert(client)

    suspend fun update(client: Client): Int = clientDao.update(client)

    suspend fun delete(client: Client): Int = clientDao.delete(client)

    suspend fun deleteById(id: Long): Int = clientDao.deleteById(id)
}
