package com.trasladossolidarios

import android.app.Application
import com.cloudinary.android.MediaManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ComposeTrasladosSolidariosApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val config = mapOf("cloud_name" to "jymfy8jm") //fix? no mostrar mi nombre
        MediaManager.init(this, config)
    }

}