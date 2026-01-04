package org.example.homeflow.feature.edit_task

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.homeflow.core.model.Membership
import org.example.homeflow.core.model.TaskCategory
import org.example.homeflow.core.model.TaskPriority
import org.example.homeflow.core.ui.components.AppDropdown
import org.example.homeflow.core.ui.components.AppTextField
import org.example.homeflow.core.ui.components.HomeFlowButton
import org.example.homeflow.feature.add_task.components.CategoryAndPriorityContainer

@Composable
fun EditTaskScreen(
    viewModel: EditTaskViewModel,
    onNavigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState) {
        if (uiState.message != null) {
            uiState.message?.let { message ->
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short
                )
            }
            viewModel.clearMessage()
        }
    }

    if (uiState.isLoadingTask) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        EditTaskContent(
            snackbarHostState = snackbarHostState,
            title = uiState.title,
            description = uiState.description,
            category = uiState.category,
            priority = uiState.priority,
            assignedTo = uiState.assignedTo,
            members = uiState.members,
            isLoading = uiState.isLoading,
            onTitleUpdate = { viewModel.updateTitle(it) },
            onDescriptionUpdate = { viewModel.updateDescription(it) },
            onCategoryUpdate = { viewModel.updateCategory(it) },
            onPriorityUpdate = { viewModel.updatePriority(it) },
            onUpdateTask = {
                viewModel.updateTask()
                onNavigateBack()
            },
            onAssign = { viewModel.updateAssignment(it) },
            onDeleteTask = {
                viewModel.deleteTask()
                onNavigateBack()
            },
            onNavigateBack = onNavigateBack
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditTaskContent(
    title: String,
    description: String?,
    category: TaskCategory,
    priority: TaskPriority,
    assignedTo: Membership?,
    isLoading: Boolean,
    snackbarHostState: SnackbarHostState,
    onTitleUpdate: (String) -> Unit,
    onDescriptionUpdate: (String) -> Unit,
    onCategoryUpdate: (TaskCategory) -> Unit,
    onPriorityUpdate: (TaskPriority) -> Unit,
    members: List<Membership>,
    onUpdateTask: () -> Unit,
    onDeleteTask: () -> Unit,
    onAssign: (Membership) -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        snackbarHost = {  SnackbarHost(hostState = snackbarHostState) },
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
                title = { Text("Edit Task", color = MaterialTheme.colorScheme.onPrimary) },
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
                    value = description ?: "",
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
                AppDropdown(
                    items = members.map { it.username },
                    selected = assignedTo?.username ?: "",
                    onSelected = { username ->
                        onAssign(members.find { membership -> membership.username == username }!!)
                    },
                    label = "Assigned to",
                    borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = null
                        )
                    },
                )
                Spacer(Modifier.weight(1f))
                HomeFlowButton(
                    onClick = onDeleteTask,
                    isLoading = isLoading,
                    text = "Delete",
                    color = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                HomeFlowButton(
                    onClick = onUpdateTask,
                    isLoading = isLoading,
                    text = "Update task",
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}