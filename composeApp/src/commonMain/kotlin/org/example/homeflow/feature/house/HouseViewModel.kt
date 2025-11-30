package org.example.homeflow.feature.house

import HouseRepositoryImpl
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.homeflow.core.data.TaskRepositoryImpl
import org.example.homeflow.core.data.repositories.HouseRepository
import org.example.homeflow.core.model.House


class HouseViewModel(
    val houseId: String,
    val houseRepository: HouseRepository
): ViewModel() {
    val taskRepository = TaskRepositoryImpl()

    private var _uiState = MutableStateFlow(HouseUiState())
    val uiState: StateFlow<HouseUiState> = _uiState.asStateFlow()

    val tasks = taskRepository.getHouseTasks(houseId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        initialize()
    }

    fun initialize() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val house = houseRepository.getHouseById(houseId)
            delay(5000)
            _uiState.update { it.copy(isLoading = false, house = house)}
        }
    }
}

data class HouseUiState(
    val house: House? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)