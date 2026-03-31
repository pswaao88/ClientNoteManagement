package com.example.ipadress.feature.pc

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
import com.example.ipadress.data.local.entity.PcEntry
import com.example.ipadress.presentation.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PcDetailScreen(
    pcId: Long,
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onEdit: (Pair<Long, Long>) -> Unit,
) {
    var current by remember { mutableStateOf<PcEntry?>(null) }

    LaunchedEffect(pcId) {
        current = viewModel.getPcEntry(pcId)
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = { Text(current?.seatSuffix?.let { "PC $it" } ?: "PC 상세") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                actions = {
                    current?.let {
                        IconButton(onClick = { onEdit(it.departmentId to it.id) }) {
                            Icon(Icons.Default.Edit, contentDescription = "PC 수정")
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
            val pc = current ?: return@Column

            DetailField("전화번호", pc.seatSuffix.orEmpty())
            DetailField("IP 주소", pc.ipAddress.orEmpty())
            DetailField("아이디", pc.loginId.orEmpty())
            DetailField("비밀번호", pc.loginPassword.orEmpty())
            DetailField("메모", pc.memo.orEmpty())
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
