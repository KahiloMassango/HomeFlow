package org.example.homeflow.feature.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.homeflow.core.data.repositories.HouseRepository
import org.example.homeflow.core.data.repositories.TaskRepository
import org.example.homeflow.core.data.util.Result
import org.example.homeflow.core.model.HouseWithMembers
import org.example.homeflow.feature.tasks.model.TaskFilter


class TasksViewModel(
    val houseId: String,
    private val houseRepository: HouseRepository,
    taskRepository: TaskRepository

) : ViewModel() {

    private var _uiState = MutableStateFlow(HouseUiState())
    val uiState: StateFlow<HouseUiState> = _uiState.asStateFlow()

    val tasks = combine(taskRepository.getHouseTasksFlow(houseId), _uiState.asStateFlow()) { tasks, state ->
        _uiState.update {
            it.copy(
                totalTasks = tasks.size,
                todoTotalTasks = tasks.count { t -> !t.done },
                doneTasks = tasks.count { t -> t.done })
        }
        when (state.taskFilter) {
            TaskFilter.All -> tasks
            TaskFilter.ToDo -> tasks.filter { !it.done }
            TaskFilter.Done -> tasks.filter { it.done }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    init {
        initialize()
    }

    fun initialize() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = houseRepository.getHouseByIdWithMembers(houseId)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false, houseWithMembers = result.data) }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            houseWithMembers = null,
                            message = "An error occurred"
                        )
                    }
                }
            }
        }
    }


    fun deleteHouse() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = houseRepository.deleteHouse(houseId)
            when (result) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false, message =  "House deleted!") }
                }

                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false, message = "An error occurred") }
                }
            }
        }
    }

    fun updateFilter(filter: TaskFilter) {
        _uiState.update { it.copy(taskFilter = filter) }
    }

    fun leaveHouse() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = houseRepository.leaveHouse(houseId)
            when (result) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false, message = "Leave successful!") }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false, message = "An error occurred") }
                }
            }
        }
    }
    fun clearMessage() {
        _uiState.update { it.copy(message = null) }
    }

}

data class HouseUiState(
    val houseWithMembers: HouseWithMembers? = null,
    val totalTasks: Int = 0,
    val todoTotalTasks: Int = 0,
    val doneTasks: Int = 0,
    val isLoading: Boolean = false,
    val taskFilter: TaskFilter = TaskFilter.All,
    val message: String? = null
)