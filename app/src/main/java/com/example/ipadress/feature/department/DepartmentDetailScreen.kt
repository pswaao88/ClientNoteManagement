package com.example.ipadress.feature.department

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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ipadress.data.local.entity.Device
import com.example.ipadress.data.local.entity.PcEntry
import com.example.ipadress.presentation.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepartmentDetailScreen(
    departmentId: Long,
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onAddDevice: () -> Unit,
    onDeviceClick: (Long) -> Unit,
    onEditDevice: (Long) -> Unit,
    onAddPc: () -> Unit,
    onPcClick: (Long) -> Unit,
    onEditPc: (Long) -> Unit,
) {
    val devices by viewModel.devices(departmentId).collectAsState()
    val pcs by viewModel.pcs(departmentId).collectAsState()
    var selectedTab by rememberSaveable(departmentId) { mutableStateOf(0) }
    var deviceQuery by rememberSaveable(departmentId) { mutableStateOf("") }
    var pcQuery by rememberSaveable(departmentId) { mutableStateOf("") }
    var title by remember { mutableStateOf("과") }

    LaunchedEffect(departmentId) {
        title = viewModel.getDepartment(departmentId)?.name ?: "과"
    }

    LaunchedEffect(departmentId, deviceQuery) {
        viewModel.updateDeviceQuery(deviceQuery)
    }

    LaunchedEffect(departmentId, pcQuery) {
        viewModel.updatePcQuery(pcQuery)
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(title)
                        Text(
                            text = if (selectedTab == 0) "기기 ${devices.size}개" else "PC ${pcs.size}개",
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
            FloatingActionButton(onClick = if (selectedTab == 0) onAddDevice else onAddPc) {
                Icon(Icons.Default.Add, contentDescription = if (selectedTab == 0) "기기 추가" else "PC 추가")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("기기") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("PC") })
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = if (selectedTab == 0) deviceQuery else pcQuery,
                    onValueChange = {
                        if (selectedTab == 0) deviceQuery = it else pcQuery = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(if (selectedTab == 0) "기기 검색" else "PC 검색") },
                    singleLine = true,
                    placeholder = {
                        Text(if (selectedTab == 0) "모델명, IP, 아이디 검색" else "전화번호, IP, 아이디 검색")
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )

                Text(
                    text = "카드를 누르면 상세, 연필 버튼은 수정",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                when {
                    selectedTab == 0 && devices.isEmpty() -> {
                        EmptyHintCard("등록된 기기가 없습니다.", "오른쪽 아래 추가 버튼으로 기기를 등록하세요.")
                    }

                    selectedTab == 1 && pcs.isEmpty() -> {
                        EmptyHintCard("등록된 PC가 없습니다.", "오른쪽 아래 추가 버튼으로 PC를 등록하세요.")
                    }

                    selectedTab == 0 -> {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            items(devices, key = Device::id) { device ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onDeviceClick(device.id) },
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
                                            CardLine("모델명", device.modelName, bold = true)
                                            CardLine("IP", device.ipAddress)
                                            CardLine("아이디", device.loginId)
                                            CardLine("비밀번호", device.loginPassword)
                                            CardLine("메모", device.memo.orEmpty(), ellipsis = true)
                                        }
                                        IconButton(onClick = { onEditDevice(device.id) }) {
                                            Icon(Icons.Default.Edit, contentDescription = "기기 수정")
                                        }
                                    }
                                }
                            }
                        }
                    }

                    else -> {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            items(pcs, key = PcEntry::id) { pc ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onPcClick(pc.id) },
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
                                            CardLine("전화번호", pc.seatSuffix.orEmpty(), bold = true)
                                            CardLine("IP", pc.ipAddress.orEmpty())
                                            CardLine("아이디", pc.loginId.orEmpty())
                                            CardLine("비밀번호", pc.loginPassword.orEmpty())
                                            CardLine("메모", pc.memo.orEmpty(), ellipsis = true)
                                        }
                                        IconButton(onClick = { onEditPc(pc.id) }) {
                                            Icon(Icons.Default.Edit, contentDescription = "PC 수정")
                                        }
                                    }
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
