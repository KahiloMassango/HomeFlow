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
import org.example.homeflow.core.data.repositories.AuthRepository
import org.example.homeflow.core.data.repositories.HouseRepository
import org.example.homeflow.core.data.util.Result

class HomeViewModel(
    private val houseRepository: HouseRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val houses = houseRepository.getHousesWithMembersFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createHouse(name : String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = houseRepository.createHouse(name)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false, houseCode = result.data) }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(message = "An error occurred", isLoading = false) }
                }
            }

        }
    }

    fun joinHouse(code : String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = houseRepository.joinHouse(code = code)

            when (result) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(message = "An error occurred", isLoading = false) }
                }
            }
        }
    }

    fun clearHouseCode() {
        _uiState.update { it.copy(houseCode = null) }
    }

    fun clearMessage() {
        _uiState.update { it.copy(message = null) }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val houseCode: String? = null
)