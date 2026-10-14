package com.trasladossolidarios.domain.common

sealed interface DomainError {
    data object InvalidMail : DomainError
    data object WeakPassword : DomainError
    data object EmptyCredentials : DomainError
    data object EmailAlreadyInUse : DomainError
    data object InvalidCredentials : DomainError
    data object Network : DomainError
    data object Unknow : DomainError
}