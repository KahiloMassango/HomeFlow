package org.example.homeflow.feature.edit_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.homeflow.core.data.repositories.MembershipRepository
import org.example.homeflow.core.data.repositories.TaskRepository
import org.example.homeflow.core.model.Membership
import org.example.homeflow.core.model.TaskCategory
import org.example.homeflow.core.model.TaskPriority
import kotlin.time.ExperimentalTime

class EditTaskViewModel(
    val houseId: String,
    val taskId: String,
    private val taskRepository: TaskRepository,
    private val membershipRepository: MembershipRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(EditTaskState())
    val uiState: StateFlow<EditTaskState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingTask = true) }
            try {
                // Load members
                val members = membershipRepository.getHouseMemberships(houseId)

                // Load existing task
                val task = taskRepository.getTaskById(taskId)
                // Find the assigned member
                val assignedMember = members.find { it.username == task.assignedTo }

                _uiState.update {
                    EditTaskState(
                        members = members,
                        title = task.title,
                        description = task.description,
                        category = task.category,
                        priority = task.priority,
                        assignedTo = assignedMember,
                        dueDate = task.dueDate,
                        isLoadingTask = false
                    )
                }
            } catch (e: Exception) {
                Logger.d(e.stackTraceToString())
                _uiState.update { it.copy(isLoadingTask = false) }
            }
        }
    }

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

    fun updateAssignment(member: Membership) {
        _uiState.update { currentState ->
            currentState.copy(assignedTo = member)
        }
    }

    fun updateTask() {
        try {
            _uiState.update { curr -> curr.copy(isLoading = true) }
            viewModelScope.launch {
                taskRepository.updateTask(
                    taskId = taskId,
                    houseId = houseId,
                    title = _uiState.value.title,
                    description = _uiState.value.description,
                    category = _uiState.value.category,
                    priority = _uiState.value.priority,
                    dueDate = _uiState.value.dueDate,
                    assignedTo = _uiState.value.assignedTo?.username
                )
                _uiState.update { curr -> curr.copy(isLoading = false) }
            }
        } catch (e: Exception) {
            Logger.d(e.stackTraceToString())
            _uiState.update { curr -> curr.copy(isLoading = false) }
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                taskRepository.deleteTask(id = taskId, houseId = houseId)
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}

data class EditTaskState @OptIn(ExperimentalTime::class) constructor(
    val members: List<Membership> = emptyList(),
    val title: String = "",
    val description: String? = null,
    val category: TaskCategory = TaskCategory.Groceries,
    val priority: TaskPriority = TaskPriority.Low,
    val isLoading: Boolean = false,
    val isLoadingTask: Boolean = false,
    val assignedTo: Membership? = null,
    val dueDate: Long? = null
)