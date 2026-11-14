package com.trasladossolidarios.location.di

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.trasladossolidarios.domain.location.LocationTracker
import com.trasladossolidarios.location.LocationTrackerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val locationModule = module {

    single<FusedLocationProviderClient> {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }

    single<LocationTracker> { LocationTrackerImpl(androidContext(), get()) }
}
