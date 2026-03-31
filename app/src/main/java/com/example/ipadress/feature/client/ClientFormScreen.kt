package com.example.ipadress.feature.client

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.ipadress.data.local.entity.Client
import com.example.ipadress.feature.common.EntityFormLayout
import com.example.ipadress.feature.common.FormField
import com.example.ipadress.presentation.AppViewModel

@Composable
fun ClientFormScreen(
    clientId: Long,
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onDone: () -> Unit,
) {
    var current by remember { mutableStateOf(Client(name = "")) }

    LaunchedEffect(clientId) {
        if (clientId != 0L) current = viewModel.getClient(clientId) ?: current
    }

    EntityFormLayout(
        title = if (clientId == 0L) "\uac70\ub798\ucc98 \ucd94\uac00" else "\uac70\ub798\ucc98 \uc218\uc815",
        onBack = onBack,
        onSave = {
            if (current.name.isBlank()) return@EntityFormLayout
            viewModel.saveClient(current)
            onDone()
        },
        onDelete = if (clientId == 0L) null else ({
            viewModel.deleteClient(current)
            onDone()
        })
    ) {
        FormField(current.name, { current = current.copy(name = it) }, "\uac70\ub798\ucc98\uba85")
        FormField(current.memo, { current = current.copy(memo = it) }, "\uba54\ubaa8")
    }
}
