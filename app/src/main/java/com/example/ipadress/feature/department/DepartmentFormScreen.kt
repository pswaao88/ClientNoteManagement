package com.example.ipadress.feature.department

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.ipadress.data.local.entity.Department
import com.example.ipadress.feature.common.EntityFormLayout
import com.example.ipadress.feature.common.FormField
import com.example.ipadress.presentation.AppViewModel

@Composable
fun DepartmentFormScreen(
    clientId: Long,
    departmentId: Long,
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onDone: () -> Unit,
) {
    var current by remember { mutableStateOf(Department(clientId = clientId, name = "")) }

    LaunchedEffect(clientId, departmentId) {
        if (departmentId != 0L) current = viewModel.getDepartment(departmentId) ?: current
    }

    EntityFormLayout(
        title = if (departmentId == 0L) "\uacfc \ucd94\uac00" else "\uacfc \uc218\uc815",
        onBack = onBack,
        onSave = {
            if (current.name.isBlank()) return@EntityFormLayout
            viewModel.saveDepartment(current.copy(clientId = clientId))
            onDone()
        },
        onDelete = if (departmentId == 0L) null else ({
            viewModel.deleteDepartment(current)
            onDone()
        })
    ) {
        FormField(current.name, { current = current.copy(name = it) }, "\uacfc\uba85")
        FormField(current.managerName, { current = current.copy(managerName = it) }, "\ub2f4\ub2f9\uc790\uba85")
        FormField(current.phone, { current = current.copy(phone = it) }, "\uc5f0\ub77d\ucc98")
        FormField(current.memo, { current = current.copy(memo = it) }, "\uba54\ubaa8")
    }
}
