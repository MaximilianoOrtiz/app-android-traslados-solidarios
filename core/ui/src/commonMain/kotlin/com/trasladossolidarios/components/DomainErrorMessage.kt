package com.trasladossolidarios.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import com.trasladossolidarios.domain.common.DomainError
import com.trasladossolidarios.ui.resources.Res
import com.trasladossolidarios.ui.resources.auth_error_email_in_use
import com.trasladossolidarios.ui.resources.auth_error_empty_credentials
import com.trasladossolidarios.ui.resources.auth_error_invalid_credentials
import com.trasladossolidarios.ui.resources.auth_error_invalid_email
import com.trasladossolidarios.ui.resources.auth_error_network
import com.trasladossolidarios.ui.resources.auth_error_unknown
import com.trasladossolidarios.ui.resources.auth_error_weak_password

@Composable
fun DomainError.toMessage(): String = when (this){
    DomainError.InvalidMail -> stringResource(Res.string.auth_error_invalid_email)
    DomainError.WeakPassword -> stringResource( Res.string.auth_error_weak_password)
    DomainError.EmptyCredentials -> stringResource(Res.string.auth_error_empty_credentials)
    DomainError.EmailAlreadyInUse -> stringResource (Res.string.auth_error_email_in_use)
    DomainError.InvalidCredentials -> stringResource (Res.string.auth_error_invalid_credentials)
    DomainError.Network -> stringResource (Res.string.auth_error_network)
    DomainError.NotFound -> stringResource (Res.string.auth_error_unknown)
    DomainError.Unknow -> stringResource (Res.string.auth_error_unknown)
}