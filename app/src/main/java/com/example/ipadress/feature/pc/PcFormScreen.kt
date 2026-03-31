package com.example.ipadress.feature.pc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.ipadress.data.local.entity.PcEntry
import com.example.ipadress.feature.common.EntityFormLayout
import com.example.ipadress.feature.common.FormField
import com.example.ipadress.feature.common.PasswordField
import com.example.ipadress.presentation.AppViewModel

@Composable
fun PcFormScreen(
    departmentId: Long,
    pcId: Long,
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onDone: () -> Unit,
) {
    var current by remember {
        mutableStateOf(
            PcEntry(
                departmentId = departmentId,
            )
        )
    }

    LaunchedEffect(departmentId, pcId) {
        if (pcId != 0L) current = viewModel.getPcEntry(pcId) ?: current
    }

    EntityFormLayout(
        title = if (pcId == 0L) "PC \ucd94\uac00" else "PC \uc218\uc815",
        onBack = onBack,
        onSave = {
            viewModel.savePcEntry(current.copy(departmentId = departmentId))
            onDone()
        },
        onDelete = if (pcId == 0L) null else ({
            viewModel.deletePcEntry(current)
            onDone()
        })
    ) {
        FormField(current.seatSuffix.orEmpty(), { current = current.copy(seatSuffix = it.ifBlank { null }) }, "\uc804\ud654\ubc88\ud638")
        FormField(current.ipAddress.orEmpty(), { current = current.copy(ipAddress = it.ifBlank { null }) }, "IP \uc8fc\uc18c")
        FormField(current.loginId.orEmpty(), { current = current.copy(loginId = it.ifBlank { null }) }, "ID")
        PasswordField(current.loginPassword.orEmpty(), { current = current.copy(loginPassword = it.ifBlank { null }) }, "PW")
        FormField(current.memo.orEmpty(), { current = current.copy(memo = it.ifBlank { null }) }, "\uba54\ubaa8")
    }
}
