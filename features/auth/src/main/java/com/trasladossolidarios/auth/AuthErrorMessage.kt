package com.trasladossolidarios.auth

import androidx.annotation.StringRes
import com.trasladossolidarios.domain.common.DomainError

@StringRes
fun DomainError.toMessageRes(): Int = when (this){
    DomainError.InvalidMail -> R.string.auth_error_invalid_email
    DomainError.WeakPassword -> R.string.auth_error_weak_password
    DomainError.EmptyCredentials -> R.string.auth_error_empty_credentials
    DomainError.EmailAlreadyInUse -> R.string.auth_error_email_in_use
    DomainError.InvalidCredentials -> R.string.auth_error_invalid_credentials
    DomainError.Network -> R.string.auth_error_network
    DomainError.Unknow -> R.string.auth_error_unknown
}
