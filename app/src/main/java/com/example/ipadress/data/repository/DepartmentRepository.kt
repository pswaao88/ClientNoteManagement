package com.example.ipadress.data.repository

import com.example.ipadress.data.local.dao.DepartmentDao
import com.example.ipadress.data.local.entity.Department
import kotlinx.coroutines.flow.Flow

class DepartmentRepository(
    private val departmentDao: DepartmentDao,
) {
    fun observeByClientId(clientId: Long): Flow<List<Department>> =
        departmentDao.observeByClientId(clientId)

    suspend fun getById(id: Long): Department? = departmentDao.getById(id)

    fun search(clientId: Long, query: String): Flow<List<Department>> {
        return if (query.isBlank()) {
            departmentDao.observeByClientId(clientId)
        } else {
            departmentDao.search(clientId, query.trim())
        }
    }

    suspend fun insert(department: Department): Long = departmentDao.insert(department)

    suspend fun update(department: Department): Int = departmentDao.update(department)

    suspend fun delete(department: Department): Int = departmentDao.delete(department)

    suspend fun deleteById(id: Long): Int = departmentDao.deleteById(id)
}
