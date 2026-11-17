package com.trasladossolidarios.profile.di

import com.trasladossolidarios.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profileViewModelModule = module {

    viewModelOf(::ProfileViewModel)
}
