package com.trasladossolidarios.shared

import android.content.Context
import com.cloudinary.android.MediaManager
import com.trasladossolidarios.auth.di.authViewModelModule
import com.trasladossolidarios.createride.di.createRideViewModelModule
import com.trasladossolidarios.data.di.dataModule
import com.trasladossolidarios.data.di.locationModuleAndroid
import com.trasladossolidarios.domain.di.domainModule
import com.trasladossolidarios.home.di.homeViewModelModule
import com.trasladossolidarios.myrides.di.myRidesViewModelModule
import com.trasladossolidarios.network.di.networkModule
import com.trasladossolidarios.network.di.networkModuleAndroid
import com.trasladossolidarios.openrides.di.openRidesViewModelModule
import com.trasladossolidarios.profile.di.profileViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Arranque de la app: Koin (11 módulos) + MediaManager (cloudinary).
 * La Application de :app solo llama a initApp(this).
 */
fun initApp(context: Context) {
    startKoin {
        androidContext(context)
        modules(
            domainModule,
            dataModule,
            networkModule,
            authViewModelModule,
            homeViewModelModule,
            myRidesViewModelModule,
            openRidesViewModelModule,
            profileViewModelModule,
            createRideViewModelModule,
            networkModuleAndroid,
            locationModuleAndroid
        )
    }

    val config = mapOf("cloud_name" to "jymfy8jm") //fix? no mostrar mi nombre
    MediaManager.init(context, config)
}
