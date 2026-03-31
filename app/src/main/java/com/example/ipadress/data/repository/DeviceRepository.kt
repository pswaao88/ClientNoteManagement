package com.example.ipadress.data.repository

import com.example.ipadress.data.local.dao.DeviceDao
import com.example.ipadress.data.local.entity.Device
import kotlinx.coroutines.flow.Flow

class DeviceRepository(
    private val deviceDao: DeviceDao,
) {
    fun observeByDepartmentId(departmentId: Long): Flow<List<Device>> =
        deviceDao.observeByDepartmentId(departmentId)

    suspend fun getById(id: Long): Device? = deviceDao.getById(id)

    fun search(departmentId: Long, query: String): Flow<List<Device>> {
        return if (query.isBlank()) {
            deviceDao.observeByDepartmentId(departmentId)
        } else {
            deviceDao.search(departmentId, query.trim())
        }
    }

    suspend fun insert(device: Device): Long = deviceDao.insert(device)

    suspend fun update(device: Device): Int = deviceDao.update(device)

    suspend fun delete(device: Device): Int = deviceDao.delete(device)

    suspend fun deleteById(id: Long): Int = deviceDao.deleteById(id)
}
