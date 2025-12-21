package org.example.homeflow.feature.add_task

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
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class AddTaskViewModel(
    val houseId: String,
    private val taskRepository: TaskRepository,
    private val membershipRepository: MembershipRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(AddTaskState())
    val uiState: StateFlow<AddTaskState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val members = membershipRepository.getHouseMemberships(houseId)
            _uiState.update { it.copy(members = members) }
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

    fun createTask() {
        try {
            _uiState.update { curr -> curr.copy(isLoading = true) }
            viewModelScope.launch {
                taskRepository.addTask(
                    houseId = houseId,
                    title = _uiState.value.title,
                    description = _uiState.value.description,
                    category = _uiState.value.category,
                    priority = _uiState.value.priority,
                    dueDate = _uiState.value.dueDate,
                    assignedTo = _uiState.value.assignedTo?.username
                )
            }
            _uiState.update { curr -> AddTaskState(members = curr.members) }
        } catch (e: Exception) {
            Logger.d(e.stackTraceToString())
        }
    }
}

data class AddTaskState @OptIn(ExperimentalTime::class) constructor(
    val members: List<Membership> = emptyList(),
    val title: String = "",
    val description: String = "",
    val category: TaskCategory = TaskCategory.Groceries,
    val priority: TaskPriority = TaskPriority.Low,
    val isLoading: Boolean = false,
    val assignedTo: Membership? = null,
    val dueDate: Long = Clock.System.now().toEpochMilliseconds()
)