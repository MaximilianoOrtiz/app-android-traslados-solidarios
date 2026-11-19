package com.trasladossolidarios

import android.app.Application
import com.trasladossolidarios.shared.initApp

class ComposeTrasladosSolidariosApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initApp(this@ComposeTrasladosSolidariosApp)
    }
}
