package com.trasladossolidarios

import android.app.Application
import com.cloudinary.android.MediaManager
import com.trasladossolidarios.auth.di.authViewModelModule
import com.trasladossolidarios.createride.di.createRideViewModelModule
import com.trasladossolidarios.data.di.dataModule
import com.trasladossolidarios.domain.di.domainModule
import com.trasladossolidarios.home.di.homeViewModelModule
import com.trasladossolidarios.location.di.locationModule
import com.trasladossolidarios.myrides.di.myRidesViewModelModule
import com.trasladossolidarios.network.di.networkModule
import com.trasladossolidarios.openrides.di.openRidesViewModelModule
import com.trasladossolidarios.profile.di.profileViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ComposeTrasladosSolidariosApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ComposeTrasladosSolidariosApp)
            modules(
                domainModule,
                dataModule,
                networkModule,
                locationModule,
                authViewModelModule,
                homeViewModelModule,
                myRidesViewModelModule,
                openRidesViewModelModule,
                profileViewModelModule,
                createRideViewModelModule
            )
        }

        val config = mapOf("cloud_name" to "jymfy8jm") //fix? no mostrar mi nombre
        MediaManager.init(this, config)
    }

}
