package com.trasladossolidarios.domain.common

//usa genericos (T), trae el dato. sealed solo se puede implmentar dentro del mismo paquete/modulo
sealed interface AppResult<out T> {
    data class Success<out T>(val data: T) : AppResult<T>
    //Nothing es ningun valor posible, sirve para el T.
    data class Error(val error: DomainError): AppResult<Nothing>
}