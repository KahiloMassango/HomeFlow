package org.example.homeflow.feature.add_task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.example.homeflow.core.model.TaskCategory
import org.example.homeflow.core.model.TaskPriority
import org.example.homeflow.core.ui.components.AppTextField
import org.example.homeflow.core.ui.components.HomeFlowButton
import org.example.homeflow.feature.add_task.components.CategoryAndPriorityContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    viewModel: AddTaskViewModel,
    onNavigateBack: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()
    AddTaskContent(
        title = uiState.title,
        description = uiState.description,
        category = uiState.category,
        priority = uiState.priority,
        isLoading = uiState.isLoading,
        onTitleUpdate = { viewModel.updateTitle(it) },
        onDescriptionUpdate = { viewModel.updateDescription(it) },
        onCategoryUpdate = { viewModel.updateCategory(it) },
        onPriorityUpdate = { viewModel.updatePriority(it) },
        onAddTask = { viewModel.createTask() },
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTaskContent(
    title: String,
    description: String,
    category: TaskCategory,
    priority: TaskPriority,
    isLoading: Boolean,
    onTitleUpdate: (String) -> Unit,
    onDescriptionUpdate: (String) -> Unit,
    onCategoryUpdate: (TaskCategory) -> Unit,
    onPriorityUpdate: (TaskPriority) -> Unit,
    onAddTask: () -> Unit,
    onNavigateBack: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                title = { Text("Add Task $title", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            )
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
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                AppTextField(
                    label = "Title *",
                    value = title,
                    onValueChange = onTitleUpdate,
                    placeholder = "e.g., Buy groceries",
                    modifier = Modifier.fillMaxWidth(),
                )

                AppTextField(
                    label = "Description (Optional)",
                    value = description,
                    onValueChange = onDescriptionUpdate,
                    placeholder = "Add more details...",
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(),
                    maxLines = 4
                )

                CategoryAndPriorityContainer(
                    category = category,
                    priority = priority,
                    onCategoryUpdate = onCategoryUpdate,
                    onPriorityUpdate = onPriorityUpdate
                )
                Spacer(Modifier.height(26.dp))

                // Outline button
                HomeFlowButton(
                    onClick = onAddTask,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (isLoading) CircularProgressIndicator() else Text("Create Task")
                }

            }
        }
    }
}

