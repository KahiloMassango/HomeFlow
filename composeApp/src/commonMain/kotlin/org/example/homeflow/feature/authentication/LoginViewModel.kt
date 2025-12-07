package org.example.homeflow.feature.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.homeflow.core.data.repositories.AuthRepository

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState());
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            val result = authRepository.login()
            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(loginSuccess = true)
                    }
                },
                onFailure = {
                    _uiState.update { it.copy(message = it.message) }
                }
            )
        }
    }
}


data class LoginUiState(
    val message: String? = null,
    val loading: Boolean = false,
    val loginSuccess: Boolean = false,
)