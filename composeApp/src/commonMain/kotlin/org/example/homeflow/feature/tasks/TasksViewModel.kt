package org.example.homeflow.feature.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.homeflow.core.data.repositories.HouseRepository
import org.example.homeflow.core.data.repositories.TaskRepository
import org.example.homeflow.core.model.HouseWithMembers
import org.example.homeflow.feature.tasks.model.TaskFilter
import kotlin.collections.emptyList
import kotlin.collections.filter


class TasksViewModel(
    val houseId: String,
    private val houseRepository: HouseRepository,
    private val taskRepository: TaskRepository

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
            val house = houseRepository.getHouseByIdWithMembers(houseId)
            delay(5000)
            _uiState.update { it.copy(isLoading = false, houseWithMembers = house) }
        }
    }


    fun deleteHouse() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                houseRepository.deleteHouse(houseId)
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun updateFilter(filter: TaskFilter) {
        _uiState.update { it.copy(taskFilter = filter) }
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