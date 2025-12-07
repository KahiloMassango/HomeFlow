package org.example.homeflow.core.data.repositories

import kotlinx.coroutines.flow.Flow
import org.example.homeflow.core.model.User

interface AuthRepository {
    val isSignedIn: Flow<Boolean>

    suspend fun getUser(): User
    suspend fun login(): Result<Unit>
    suspend fun logout(): Result<Unit>
}