package com.trasladossolidarios.network.result

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data object NetworkError : NetworkResult<Nothing>
    data object PermissionDenied : NetworkResult<Nothing>
    data object Unknown : NetworkResult<Nothing>
}