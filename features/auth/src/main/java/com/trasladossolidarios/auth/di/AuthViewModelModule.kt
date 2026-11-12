package com.trasladossolidarios.auth.di

import com.trasladossolidarios.auth.AuthViewModel
import com.trasladossolidarios.login.SessionViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module {

    viewModelOf(::AuthViewModel)
    viewModelOf(::SessionViewModel)
}
