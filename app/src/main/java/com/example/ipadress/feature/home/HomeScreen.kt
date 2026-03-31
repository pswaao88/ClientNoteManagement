package com.example.ipadress.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ipadress.data.local.entity.Client
import com.example.ipadress.presentation.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: AppViewModel,
    onClientClick: (Long) -> Unit,
    onAddClient: () -> Unit,
    onEditClient: (Long) -> Unit,
) {
    val clients by viewModel.clients.collectAsState()
    var query by remember { mutableStateOf("") }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("거래처 기기관리")
                        Text(
                            text = "거래처 ${clients.size}개",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClient) {
                Icon(Icons.Default.Add, contentDescription = "거래처 추가")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    viewModel.updateClientQuery(it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("거래처 검색") },
                singleLine = true,
                placeholder = { Text("거래처명이나 메모 검색") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            if (clients.isEmpty()) {
                EmptyHintCard(
                    title = "등록된 거래처가 없습니다.",
                    body = "오른쪽 아래 추가 버튼으로 첫 거래처를 등록하세요."
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(clients, key = Client::id) { client ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onClientClick(client.id) },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(0.82f),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    CardLine("거래처명", client.name, bold = true)
                                    CardLine("메모", client.memo, ellipsis = true)
                                }
                                IconButton(onClick = { onEditClient(client.id) }) {
                                    Icon(Icons.Default.Edit, contentDescription = "거래처 수정")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyHintCard(
    title: String,
    body: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(body)
        }
    }
}

@Composable
private fun CardLine(
    label: String,
    value: String,
    bold: Boolean = false,
    ellipsis: Boolean = false,
) {
    Text(
        text = "$label: ${value.ifBlank { "-" }}",
        fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
        maxLines = if (ellipsis) 1 else Int.MAX_VALUE,
        overflow = if (ellipsis) TextOverflow.Ellipsis else TextOverflow.Clip
    )
}
