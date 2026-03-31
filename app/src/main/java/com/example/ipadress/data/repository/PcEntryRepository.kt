package com.example.ipadress.data.repository

import com.example.ipadress.data.local.dao.PcEntryDao
import com.example.ipadress.data.local.entity.PcEntry
import kotlinx.coroutines.flow.Flow

class PcEntryRepository(
    private val pcEntryDao: PcEntryDao,
) {
    fun observeByDepartmentId(departmentId: Long): Flow<List<PcEntry>> =
        pcEntryDao.observeByDepartmentId(departmentId)

    suspend fun getById(id: Long): PcEntry? = pcEntryDao.getById(id)

    fun search(departmentId: Long, query: String): Flow<List<PcEntry>> {
        return if (query.isBlank()) {
            pcEntryDao.observeByDepartmentId(departmentId)
        } else {
            pcEntryDao.search(departmentId, query.trim())
        }
    }

    suspend fun insert(pcEntry: PcEntry): Long = pcEntryDao.insert(pcEntry)

    suspend fun update(pcEntry: PcEntry): Int = pcEntryDao.update(pcEntry)

    suspend fun delete(pcEntry: PcEntry): Int = pcEntryDao.delete(pcEntry)

    suspend fun deleteById(id: Long): Int = pcEntryDao.deleteById(id)
}
