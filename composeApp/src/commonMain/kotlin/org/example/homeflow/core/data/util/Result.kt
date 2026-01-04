package org.example.homeflow.core.data.util

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val message: String? = null) : Result<T>()
}