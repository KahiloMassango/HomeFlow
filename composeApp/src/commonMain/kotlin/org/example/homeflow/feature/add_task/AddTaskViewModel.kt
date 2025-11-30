package org.example.homeflow.feature.add_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.example.homeflow.core.data.repositories.TaskRepository
import org.example.homeflow.core.model.TaskCategory
import org.example.homeflow.core.model.TaskPriority
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class AddTaskViewModel(
    val houseId: String,
    private val taskRepository: TaskRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(AddTaskState())
    val uiState: StateFlow<AddTaskState> = _uiState.asStateFlow()

    fun updateTitle(value: String) {
        _uiState.update { currentState ->
            currentState.copy(title = value)
        }
    }

    fun updateDescription(value: String) {
        _uiState.update { currentState ->
            currentState.copy(description = value)
        }
    }

    fun updateCategory(category: TaskCategory) {
        _uiState.update { currentState ->
            currentState.copy(category = category)
        }
    }

    fun updatePriority(priority: TaskPriority) {
        _uiState.update { currentState ->
            currentState.copy(priority = priority)
        }
    }

    fun clearState() {
        _uiState.update { AddTaskState() }
    }

    fun createTask() {
        viewModelScope.launch {
            _uiState.update { curr -> curr.copy(isLoading = true) }
            taskRepository.addTask(
                houseId = houseId,
                title = _uiState.value.title,
                description = _uiState.value.description,
                category = _uiState.value.category,
                priority = _uiState.value.priority,
                dueDate = _uiState.value.dueDate,
                assignedTo = null
            )
            _uiState.update { curr -> curr.copy(isLoading = false) }
        }
    }
}

data class AddTaskState @OptIn(ExperimentalTime::class) constructor(
    val title: String = "",
    val description: String = "",
    val category: TaskCategory = TaskCategory.Groceries,
    val priority: TaskPriority = TaskPriority.Low,
    val isLoading: Boolean = false,
    val dueDate: Long = Clock.System.now().toEpochMilliseconds(),
)