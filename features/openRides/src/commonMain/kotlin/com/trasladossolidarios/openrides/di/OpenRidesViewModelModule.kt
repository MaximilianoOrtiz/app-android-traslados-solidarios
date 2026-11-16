package com.trasladossolidarios.openrides.di

import com.trasladossolidarios.openrides.detail.RideDetailViewModel
import com.trasladossolidarios.openrides.list.OpenRidesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val openRidesViewModelModule = module {

    viewModelOf(::OpenRidesViewModel)

    viewModel { (rideId: String) ->
        RideDetailViewModel(rideId, get(), get(), get())
    }
}
