package org.example.homeflow.feature.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.homeflow.feature.tasks.components.HouseTopBar
import org.example.homeflow.feature.tasks.components.TaskCard
import org.example.homeflow.feature.tasks.components.TaskFilter

@Composable
fun HouseScreen(
    viewModel: HouseViewModel,
    onAddTask: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val tasks by viewModel.tasks.collectAsState()

    var filter by rememberSaveable { mutableStateOf("1") }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {

        Scaffold(
            topBar = {
                HouseTopBar(
                    houseName = uiState.house?.name ?: "",
                    tasksToComplete = tasks.size,
                    oNavigateBack = onNavigateBack
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onAddTask(viewModel.houseId) },
                    containerColor = Color(0xFF2196F3),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add New Task",
                        tint = Color.White,
                    )
                }
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.surface,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    // Stats row
                    TaskFilter(
                        filter = filter,
                        onFilterChange = { filter = it },
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    // Tasks list
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(tasks) { task ->
                            TaskCard(task = task, onClick = {})
                        }
                    }
                }
            }
        }
    }
}




