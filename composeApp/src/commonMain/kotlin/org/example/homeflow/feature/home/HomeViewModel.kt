package org.example.homeflow.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.homeflow.core.data.repositories.HouseRepository

class HomeViewModel(
    val houseRepository: HouseRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val houses = houseRepository.getHousesWithMembers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createHouse(name : String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val houseCode = houseRepository.createHouse(name)
            _uiState.update { it.copy(isLoading = false, houseCode = houseCode, houseCreated = true) }
        }
    }

    fun joinHouse(code : String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            houseRepository.joinHouse(code = code)
            _uiState.update { it.copy(isLoading = false, houseJoined = true) }
        }
    }

    fun clearHouseCode() {
        _uiState.update { it.copy(houseCode = null) }
    }

    fun clearHouseCreatedAndJoined() {
        _uiState.update { it.copy(houseJoined = false, houseCreated = false) }
    }

    fun clearMessage() {
        _uiState.update { it.copy(message = null) }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val houseCreated: Boolean = false,
    val houseJoined: Boolean = false,
    val message: String? = null,
    val houseCode: String? = null
)