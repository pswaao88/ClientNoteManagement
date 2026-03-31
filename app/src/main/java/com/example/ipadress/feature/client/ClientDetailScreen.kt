package com.example.ipadress.feature.client

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ipadress.data.local.entity.Department
import com.example.ipadress.presentation.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDetailScreen(
    clientId: Long,
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onAddDepartment: () -> Unit,
    onDepartmentClick: (Long) -> Unit,
    onEditDepartment: (Long) -> Unit,
) {
    val departments by viewModel.departments(clientId).collectAsState()
    var query by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("거래처") }

    LaunchedEffect(clientId) {
        title = viewModel.getClient(clientId)?.name ?: "거래처"
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(title)
                        Text(
                            text = "과 ${departments.size}개",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddDepartment) {
                Icon(Icons.Default.Add, contentDescription = "과 추가")
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
                    viewModel.updateDepartmentQuery(it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("과 검색") },
                singleLine = true,
                placeholder = { Text("과명, 담당자명, 연락처 검색") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            if (departments.isEmpty()) {
                EmptyHintCard(
                    title = "등록된 과가 없습니다.",
                    body = "오른쪽 아래 추가 버튼으로 과를 등록하세요."
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(departments, key = Department::id) { department ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onDepartmentClick(department.id) },
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
                                    CardLine("과명", department.name, bold = true)
                                    CardLine("담당자명", department.managerName)
                                    CardLine("연락처", department.phone)
                                    CardLine("메모", department.memo, ellipsis = true)
                                }
                                IconButton(onClick = { onEditDepartment(department.id) }) {
                                    Icon(Icons.Default.Edit, contentDescription = "과 수정")
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
