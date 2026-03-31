package com.example.ipadress.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ipadress.data.local.entity.Client
import com.example.ipadress.data.local.entity.Department
import com.example.ipadress.data.local.entity.Device
import com.example.ipadress.data.local.entity.PcEntry
import com.example.ipadress.data.repository.ClientRepository
import com.example.ipadress.data.repository.DepartmentRepository
import com.example.ipadress.data.repository.DeviceRepository
import com.example.ipadress.data.repository.PcEntryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModel(
    private val clientRepository: ClientRepository,
    private val departmentRepository: DepartmentRepository,
    private val deviceRepository: DeviceRepository,
    private val pcEntryRepository: PcEntryRepository,
) : ViewModel() {
    private val clientQuery = MutableStateFlow("")
    private val departmentQuery = MutableStateFlow("")
    private val deviceQuery = MutableStateFlow("")
    private val pcQuery = MutableStateFlow("")

    val clients: StateFlow<List<Client>> = clientQuery
        .flatMapLatest { clientRepository.search(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun departments(clientId: Long): StateFlow<List<Department>> = departmentQuery
        .flatMapLatest { departmentRepository.search(clientId, it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun devices(departmentId: Long): StateFlow<List<Device>> = deviceQuery
        .flatMapLatest { deviceRepository.search(departmentId, it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun pcs(departmentId: Long): StateFlow<List<PcEntry>> = pcQuery
        .flatMapLatest { pcEntryRepository.search(departmentId, it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun updateClientQuery(query: String) {
        clientQuery.value = query
    }

    fun updateDepartmentQuery(query: String) {
        departmentQuery.value = query
    }

    fun updateDeviceQuery(query: String) {
        deviceQuery.value = query
    }

    fun updatePcQuery(query: String) {
        pcQuery.value = query
    }

    suspend fun getClient(id: Long): Client? = clientRepository.getById(id)
    suspend fun getDepartment(id: Long): Department? = departmentRepository.getById(id)
    suspend fun getDevice(id: Long): Device? = deviceRepository.getById(id)
    suspend fun getPcEntry(id: Long): PcEntry? = pcEntryRepository.getById(id)

    fun saveClient(client: Client) {
        viewModelScope.launch {
            if (client.id == 0L) clientRepository.insert(client) else clientRepository.update(client)
        }
    }

    fun saveDepartment(department: Department) {
        viewModelScope.launch {
            if (department.id == 0L) departmentRepository.insert(department) else departmentRepository.update(department)
        }
    }

    fun saveDevice(device: Device) {
        viewModelScope.launch {
            if (device.id == 0L) deviceRepository.insert(device) else deviceRepository.update(device)
        }
    }

    fun savePcEntry(pcEntry: PcEntry) {
        viewModelScope.launch {
            if (pcEntry.id == 0L) pcEntryRepository.insert(pcEntry) else pcEntryRepository.update(pcEntry)
        }
    }

    fun deleteClient(client: Client) {
        viewModelScope.launch { clientRepository.delete(client) }
    }

    fun deleteDepartment(department: Department) {
        viewModelScope.launch { departmentRepository.delete(department) }
    }

    fun deleteDevice(device: Device) {
        viewModelScope.launch { deviceRepository.delete(device) }
    }

    fun deletePcEntry(pcEntry: PcEntry) {
        viewModelScope.launch { pcEntryRepository.delete(pcEntry) }
    }

    companion object {
        fun factory(
            clientRepository: ClientRepository,
            departmentRepository: DepartmentRepository,
            deviceRepository: DeviceRepository,
            pcEntryRepository: PcEntryRepository,
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AppViewModel(
                        clientRepository,
                        departmentRepository,
                        deviceRepository,
                        pcEntryRepository,
                    ) as T
                }
            }
        }
    }
}
