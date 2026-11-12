package com.trasladossolidarios.home.di

import com.trasladossolidarios.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeViewModelModule = module {

    viewModelOf(::HomeViewModel)
}
