package com.trasladossolidarios.createride.di

import com.trasladossolidarios.createride.CreateRideViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val createRideViewModelModule = module {

    viewModelOf(::CreateRideViewModel)
}
