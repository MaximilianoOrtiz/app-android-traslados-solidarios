package com.trasladossolidarios.myrides.di

import com.trasladossolidarios.myrides.MyRidesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val myRidesViewModelModule = module {

    viewModelOf(::MyRidesViewModel)
}
