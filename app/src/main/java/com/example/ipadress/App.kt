package com.example.ipadress

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ipadress.data.local.AppDatabase
import com.example.ipadress.data.repository.ClientRepository
import com.example.ipadress.data.repository.DepartmentRepository
import com.example.ipadress.data.repository.DeviceRepository
import com.example.ipadress.data.repository.PcEntryRepository
import com.example.ipadress.navigation.AppNavGraph
import com.example.ipadress.presentation.AppViewModel

@Composable
fun App(context: Context) {
    val database = remember { AppDatabase.getInstance(context) }
    val clientRepository = remember { ClientRepository(database.clientDao()) }
    val departmentRepository = remember { DepartmentRepository(database.departmentDao()) }
    val deviceRepository = remember { DeviceRepository(database.deviceDao()) }
    val pcEntryRepository = remember { PcEntryRepository(database.pcEntryDao()) }
    val viewModel: AppViewModel = viewModel(
        factory = AppViewModel.factory(
            clientRepository,
            departmentRepository,
            deviceRepository,
            pcEntryRepository,
        )
    )

    AppNavGraph(viewModel = viewModel)
}
