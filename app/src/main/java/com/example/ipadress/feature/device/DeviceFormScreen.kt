package com.example.ipadress.feature.device

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.ipadress.data.local.entity.Device
import com.example.ipadress.feature.common.EntityFormLayout
import com.example.ipadress.feature.common.FormField
import com.example.ipadress.feature.common.PasswordField
import com.example.ipadress.presentation.AppViewModel

@Composable
fun DeviceFormScreen(
    departmentId: Long,
    deviceId: Long,
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onDone: () -> Unit,
) {
    var current by remember {
        mutableStateOf(
            Device(
                departmentId = departmentId,
                modelName = "",
                ipAddress = "",
            )
        )
    }

    LaunchedEffect(departmentId, deviceId) {
        if (deviceId != 0L) current = viewModel.getDevice(deviceId) ?: current
    }

    EntityFormLayout(
        title = if (deviceId == 0L) "\uae30\uae30 \ucd94\uac00" else "\uae30\uae30 \uc218\uc815",
        onBack = onBack,
        onSave = {
            if (current.modelName.isBlank()) return@EntityFormLayout
            viewModel.saveDevice(current.copy(departmentId = departmentId))
            onDone()
        },
        onDelete = if (deviceId == 0L) null else ({
            viewModel.deleteDevice(current)
            onDone()
        })
    ) {
        FormField(current.modelName, { current = current.copy(modelName = it) }, "\ubaa8\ub378\uba85")
        FormField(current.ipAddress, { current = current.copy(ipAddress = it) }, "IP \uc8fc\uc18c")
        FormField(current.loginId, { current = current.copy(loginId = it) }, "ID")
        PasswordField(current.loginPassword, { current = current.copy(loginPassword = it) }, "PW")
        FormField(current.memo.orEmpty(), { current = current.copy(memo = it.ifBlank { null }) }, "\uba54\ubaa8")
    }
}
