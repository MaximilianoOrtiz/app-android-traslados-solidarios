package com.trasladossolidarios.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.trasladossolidarios.domain.common.DomainError
import com.ui.R

@Composable
fun DomainError.toMessage(): String = when (this){
    DomainError.InvalidMail -> stringResource(R.string.auth_error_invalid_email)
    DomainError.WeakPassword -> stringResource( R.string.auth_error_weak_password)
    DomainError.EmptyCredentials -> stringResource(R.string.auth_error_empty_credentials)
    DomainError.EmailAlreadyInUse -> stringResource (R.string.auth_error_email_in_use)
    DomainError.InvalidCredentials -> stringResource (R.string.auth_error_invalid_credentials)
    DomainError.Network -> stringResource (R.string.auth_error_network)
    DomainError.NotFound -> stringResource (R.string.auth_error_unknown)
    DomainError.Unknow -> stringResource (R.string.auth_error_unknown)
}