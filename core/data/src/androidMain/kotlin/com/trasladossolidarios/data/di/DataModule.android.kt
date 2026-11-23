package com.trasladossolidarios.data.di

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.trasladossolidarios.data.repository.LocationRepositoryImpl
import com.trasladossolidarios.domain.repository.LocationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val locationModuleAndroid = module {

    single<FusedLocationProviderClient> {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }

    single<LocationRepository> { LocationRepositoryImpl(androidContext(), get()) }
}
