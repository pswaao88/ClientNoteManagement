package com.example.ipadress.feature.device

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ipadress.data.local.entity.Device
import com.example.ipadress.presentation.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceDetailScreen(
    deviceId: Long,
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onEdit: (Pair<Long, Long>) -> Unit,
) {
    var current by remember { mutableStateOf<Device?>(null) }

    LaunchedEffect(deviceId) {
        current = viewModel.getDevice(deviceId)
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = { Text(current?.modelName ?: "기기 상세") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                actions = {
                    current?.let {
                        IconButton(onClick = { onEdit(it.departmentId to it.id) }) {
                            Icon(Icons.Default.Edit, contentDescription = "기기 수정")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val device = current ?: return@Column

            DetailField("모델명", device.modelName)
            DetailField("IP 주소", device.ipAddress)
            DetailField("아이디", device.loginId)
            DetailField("비밀번호", device.loginPassword)
            DetailField("메모", device.memo.orEmpty())
        }
    }
}

@Composable
private fun DetailField(
    label: String,
    value: String,
    showAction: Boolean = false,
    action: @Composable (() -> Unit)? = null,
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(label, fontWeight = FontWeight.Bold)
        if (showAction && action != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(if (value.isBlank()) "-" else value)
                action()
            }
        } else {
            Text(if (value.isBlank()) "-" else value)
        }
        HorizontalDivider()
    }
}
